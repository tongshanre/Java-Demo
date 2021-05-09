package net;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SocketDemo {
    @Test
    public void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        //serverSocket.setSoTimeout(1000);
        System.out.println("start server....");
        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("localAddress:" + accept.getLocalAddress());
            System.out.println("accept:" + accept);
        }
    }


    @Test
    public void client() throws IOException {
        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("localAddress:"+ socket.getLocalAddress()+"-"+socket.getLocalPort()+"-"+socket.getPort());
        socket.getInputStream().read(new byte[1024]);
    }

    @Test
    public void ChannelClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        new Thread(() -> {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("关闭网络连接");
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        socketChannel.read(ByteBuffer.allocate(1023));
    }

    @Test
    public void testSelector() throws IOException, InterruptedException {
        Selector selector = Selector.open();
        Thread t = new Thread(() -> {

            try {
                while (selector.select() > 0) {
                    System.out.println("aaaaa");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        t.start();
        selector.close();
        Thread.sleep(1000 * 5);
    }
}
