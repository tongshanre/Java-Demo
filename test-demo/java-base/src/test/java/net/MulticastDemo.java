package net;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

public class MulticastDemo {

    private static final String address = "228.0.0.4";

    private static final int port = 8000;

    @Test
    public void node1() {

        new Thread(() -> {
            try {
                InetAddress group = InetAddress.getByName(address);
                MulticastSocket mcs = new MulticastSocket();
                //mcs.joinGroup(group);
                while (true) {
                    String msg = "Hello from node1";
                    byte[] buffer = msg.getBytes();
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, 8000);
                    mcs.send(dp);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void node2() throws InterruptedException {
        new Thread(() -> {
            try {
                InetAddress group = InetAddress.getByName(address);
                MulticastSocket mcs = new MulticastSocket(port);
                mcs.joinGroup(group);
                byte[] buffer = new byte[1024 * 4];
                while (true) {
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                    mcs.receive(dp);
                    System.out.println("receive:" + new String(dp.getData(), 0, dp.getLength()));
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new CountDownLatch(1).await();
    }

}
