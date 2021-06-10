package net;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        boolean flag = socketChannel.connect(new InetSocketAddress("localhost", 1024));
        System.out.println(flag);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 4);
        byteBuffer.put("hello".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        while (true) {
            byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("Read:" + new String(byteBuffer.array(), 0, byteBuffer.limit()) + "\t:" + new Date());
            byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("haha".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }

}
