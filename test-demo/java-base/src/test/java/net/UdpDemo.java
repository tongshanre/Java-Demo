package net;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpDemo {

    @Test
    public void testUdp() throws IOException {
        //服务
        new Thread(() -> {
            try {

                DatagramSocket datagramSocket = new DatagramSocket(10010);
                while (true) {
                    byte[] buffer = new byte[1024 * 4];
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, 1024 * 4);
                    datagramSocket.receive(datagramPacket);
                    System.out.println("server: " + new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
                    datagramPacket.setData("YES OK!".getBytes());
                    datagramSocket.send(datagramPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        // 客户端
        String msg = "Hello UDP";
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("localhost"), 10010);

        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.send(datagramPacket);
        datagramSocket.receive(datagramPacket);
        System.out.println("client: " + new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
    }

}
