import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class IODemo {

    public void bio() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                while (true) {
                    Socket accept = serverSocket.accept();
                    accept.getOutputStream().write("Hello".getBytes());
                    accept.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8080));
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = is.read(buffer);
            System.out.println(new String(buffer, 0, n));
            System.out.println(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NIO() throws IOException {
        final Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        new Thread(() -> {
            while (true) {
                try {
                    if (selector.select() > 0) {
                        System.out.println("select finish!");
                        selector.keys().stream().forEach(selectionKey -> {
                            switch (selectionKey.interestOps()) {
                                case SelectionKey.OP_ACCEPT:
                                    try {
                                        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                                        SocketChannel acceptChannel = ssc.accept();
                                        acceptChannel.configureBlocking(false);
                                        acceptChannel.register(selector, SelectionKey.OP_READ);
                                    } catch (ClosedChannelException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case SelectionKey.OP_READ:
                                    try {
                                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);
                                        channel.read(byteBuffer);
                                        byteBuffer.flip();
                                        System.out.println("read: " + new String(byteBuffer.array()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);
        byteBuffer.put("Hello NIO".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open();
        assc.bind(new InetSocketAddress("localhost", 8080));
        assc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Object o) {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);
                    asynchronousSocketChannel.read(byteBuffer);
                    System.out.println("Thread:" + Thread.currentThread().getId() + "   read：" + new String(byteBuffer.array()));
                    assc.accept(null, this);
                } finally {
                    try {
                        asynchronousSocketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable throwable, Object o) {
                System.err.println("accept error!!!");
            }
        });
        // client
        AsynchronousSocketChannel asc = AsynchronousSocketChannel.open();
        asc.connect(new InetSocketAddress("localhost", 8080), null, new CompletionHandler<Void, Void>() {

            @Override
            public void completed(Void aVoid, Void aVoid2) {
                System.out.println("Thread:" + Thread.currentThread().getId() + "   connect success ! ");
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put("Hello AIO".getBytes());
                byteBuffer.flip();
                asc.write(byteBuffer);
            }

            @Override
            public void failed(Throwable throwable, Void aVoid) {
                System.out.println("connect failed ! ");
            }
        });
        System.out.println("Thread：" + Thread.currentThread().getId());
        Thread.sleep(1000 * 3);
    }
}
