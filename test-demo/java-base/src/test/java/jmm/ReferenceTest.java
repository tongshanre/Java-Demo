package jmm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class ReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<Integer> queue = new ReferenceQueue<>();
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(queue.remove());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100);
        PhantomReference<Integer> reference = new PhantomReference<>(new Integer(1024), queue);
        System.out.println(reference.get());
    }
}
