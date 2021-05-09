package com.tong.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RedisTest {

    public void testRedis() throws InterruptedException {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("haha", "hello");
        System.out.println(jedis.get("haha"));
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for (int j = 0; j < 4; j++) {
            new Thread(() -> {
                Jedis client = new Jedis("localhost", 6379);
                for (int i = 0; i < 5000; i++) {
                    client.get("haha");
                }
                client.close();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    public void testRuntime() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(String.format("totalMem:%f , maxMem:%f", runtime.totalMemory() / Math.pow(1024, 2), runtime.maxMemory() / Math.pow(1024, 2)));
        SocketChannel socketChannel;
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    }

    @Test
    public void testFD() throws IOException {
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        fos.write("哈哈哈".getBytes());
    }

    public RedisTest() {

    }
    public static void test(Object[] params, int a) {


    }
    public static void main(String[] args) throws InterruptedException {
        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        new Thread(() -> {
            try {
                while (true){
                    queue.put(queue.size());
                    System.out.println("put:" + queue.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("take:"+queue.take());
        Semaphore semaphore=new Semaphore(1);
        semaphore.tryAcquire(1, TimeUnit.SECONDS);
    }
}
