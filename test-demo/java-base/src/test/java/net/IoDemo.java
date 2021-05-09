package net;

import org.junit.Test;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;

public class IoDemo {

    @Test
    public void testIoRead() throws IOException {
        System.out.println(Thread.currentThread().getId() + "- start");
        byte[] buffer = new byte[1024];
        int n = System.in.read(buffer);
        System.out.println(Thread.currentThread().getId() + "-" + new String(buffer, 0, n));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    new IoDemo().testIoRead();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
