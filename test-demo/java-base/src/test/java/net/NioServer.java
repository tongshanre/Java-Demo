package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {


    public void NioTest() throws IOException {
        final Selector selectorOne = Selector.open();
        final Selector selectorTwo = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 1024));
        // 注册 Accept连接事件
        serverSocketChannel.register(selectorOne, SelectionKey.OP_ACCEPT);
        new BossSelector(selectorOne, selectorTwo).start();
        new WorkSelector(selectorTwo).start();
    }

    class BossSelector extends Thread {

        private Selector boss, work;

        public BossSelector(Selector boss, Selector work) {
            this.boss = boss;
            this.work = work;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    if (boss.select() > 0) {
                        Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();
                            switch (selectionKey.readyOps()) {
                                case SelectionKey.OP_ACCEPT:
                                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                                    SocketChannel socketChannel = serverSocketChannel.accept();
                                    socketChannel.configureBlocking(false);
                                    System.out.println("ACCEPT:" + socketChannel.getRemoteAddress());
                                    // 注册读事件
                                    socketChannel.register(work, SelectionKey.OP_READ);
                                    break;
                                case SelectionKey.OP_READ:
                                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                                    String msg = readMsg(channel);
                                    System.out.println("READ:" + msg);
                                    writeMsg(channel, "ECHO");
                                    channel.register(selectionKey.selector(), SelectionKey.OP_READ);
                                    break;
                                default:
                                    System.out.println("Unsolved Ops");
                            }
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class WorkSelector extends Thread {
        private Selector work;

        public WorkSelector(Selector work) {
            this.work = work;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (work.select() > 0) {
                        System.out.println("work scan");
                        Iterator<SelectionKey> iterator = work.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();
                            switch (selectionKey.readyOps()) {
                                case SelectionKey.OP_READ:
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    String msg = readMsg(socketChannel);
                                    System.out.println("READ:" + msg);
                                    writeMsg(socketChannel, "ECHO");
                                    socketChannel.register(work, SelectionKey.OP_READ);
                                    break;
                                default:
                                    System.out.println("Unsolved Ops");
                            }
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    private void writeMsg(SocketChannel socketChannel, String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(msg.length());
        buffer.put(msg.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    private String readMsg(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        buffer.flip();
        return new String(buffer.array(), 0, buffer.limit());
    }


    public static void main(String[] args) throws IOException {
        new NioServer().NioTest();
    }
}
