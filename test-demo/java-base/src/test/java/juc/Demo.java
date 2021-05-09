package juc;

import org.junit.Test;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class Demo {

    /**
     * 测试Synchronized封装的代码是否会被interrupt打断？
     * 结果证明无法被打断
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void interruptSynchronized() throws InterruptedException, IOException {
        Object obj = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (obj) {
                try {
                    System.out.println(Thread.currentThread().getId() + " is runing");
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (obj) {
                try {
                    System.out.println(Thread.currentThread().getId() + " is runing");
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        System.in.read();
    }

    /**
     * 测试ReentrantLock的可中断性
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void interruptLock() throws InterruptedException, IOException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getId() + " is runing");
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked())
                    lock.unlock();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getId() + " is runing");
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked())
                    lock.unlock();
            }
        });
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        System.in.read();
    }

    /**
     * 测试ReentrantReadWriteLock
     *
     * @throws IOException
     */
    public void readWriteLockTest() throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        // Thread1
        Thread t1 = new Thread(() -> {
            writeLock.lock();
            System.out.println(Thread.currentThread().getId() + " get lock");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " exit");
            writeLock.unlock();
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            readLock.lock();
            System.out.println(Thread.currentThread().getId() + " get lock");
            System.out.println(Thread.currentThread().getId() + " exit");
            readLock.unlock();
        });
        t2.start();

        System.in.read();
    }

    /**
     * 测试wait方法是否释放 线程获取的锁
     * 结论 释放,而且在调用wait和notify的之前必须拿到锁对象，在锁对象上wait和notify
     *
     * @throws IOException
     */
    public void waitReleaseLockTest() throws IOException {
        Object obj = new Object();
        Object wait = new Object();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getId() + " enter");
                try {
                    Thread.sleep(1000 * 3);
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + " exit");
            }
        }).start();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getId() + " enter");
                System.out.println(Thread.currentThread().getId() + " exit");
                obj.notify();
            }
        }).start();
        System.in.read();
    }

    /**
     * 测试Lock的Condition
     *
     * @throws IOException
     */
    public void conditionTest() throws IOException {
        Lock lock = new ReentrantLock();
        Condition fullConditon = lock.newCondition();
        Condition emptyCondition = lock.newCondition();
        List<Long> list = new ArrayList<>(10);
        //生产者
        new Thread(() -> {
            while (true) {
                lock.lock();
                // 1. 队列已满，线程等待
                if (list.size() == 10) {
                    try {
                        fullConditon.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 2. 生产新的元素
                list.add(System.currentTimeMillis());
                // 3. 加入第一个元素唤醒
                if (list.size() == 1) {
                    emptyCondition.signal();
                }
                lock.unlock();
            }
        }).start();
        // 消费者
        new Thread(() -> {
            while (true) {
                lock.lock();
                // 1. 队列已空等待
                if (list.size() == 0) {
                    try {
                        emptyCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Long item = list.remove(0);
                System.out.println("consume:" + item);
                //2. 如果满队的第一个消息，唤醒
                if (list.size() == 9) {
                    fullConditon.signal();
                }
                lock.unlock();
            }
        }).start();
        System.in.read();
    }

    Integer num = 0;


    /**
     * 测试同步锁和原子类进行累加时耗时情况
     * 结论：性能相差在10倍左右
     *
     * @throws InterruptedException
     */
    public void LockAndAtomicCompareTest() throws InterruptedException {
        final int count = 100000;
        final CountDownLatch countDownLatch = new CountDownLatch(4);
        // 1. 使用同步锁
        long start = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    synchronized (this) {
                        num = num + 1;
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("同步锁用时:" + (end - start) + " total:" + num);
        // 2. 使用原子变量
        AtomicInteger atomNum = new AtomicInteger();
        CountDownLatch countDownLatch2 = new CountDownLatch(4);
        start = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    atomNum.incrementAndGet();
                }
                countDownLatch2.countDown();
            }).start();
        }
        countDownLatch2.await();
        end = System.currentTimeMillis();
        System.out.println("原子类用时:" + (end - start) + " total:" + atomNum.get());
    }

    Map<String, String> deploy = null;

    /**
     * 测试不安全的发布
     *
     * @throws InterruptedException
     */
    public void deployTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            deploy = new HashMap<String, String>();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deploy.put("frigg", "frigg");
            countDownLatch.countDown();
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("deploy:" + deploy);
                if (deploy != null) {
                    System.out.println("frigg:" + deploy.get("frigg"));
                    break;
                }
            }
            countDownLatch.countDown();
        });
        t2.start();
        t1.start();
        countDownLatch.await();
    }


    Integer lcCount = 0;
    Object lockObj = new Object();

    /**
     * 测试同步锁对变量的作用
     *
     * @throws InterruptedException
     */
    public void lockConsistancyTest() throws InterruptedException {
        final int SIZE = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int j = 0; j < 4; j++) {
            new Thread(() -> {
                for (int i = 0; i < SIZE; i++) {
                    synchronized (lockObj) {
                        lcCount++;
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(lcCount);
    }

    /**
     * 测试exchange
     *
     * @throws IOException
     */
    public void exchangeTest() throws IOException {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            String item = "轮子";
            System.out.println("线程" + Thread.currentThread().getId() + " 拿着 " + item);
            try {
                item = exchanger.exchange(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getId() + " 交换到了 " + item);
        }).start();
        new Thread(() -> {
            String item = "橘子";
            System.out.println("线程" + Thread.currentThread().getId() + " 拿着 " + item);
            try {
                item = exchanger.exchange(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getId() + " 交换到了 " + item);
        }).start();
        System.in.read();
    }

    /**
     * 测试CyclicBarrier
     *
     * @throws IOException
     */
    public void cyclicBarrierTest() throws IOException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getId() + " arrive barrier");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + " broke barrier");
            }).start();
        }
        System.in.read();
    }

    /**
     * 测试Unsafe的park和unpark挂起线程
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InterruptedException
     */
    public void parkUnparkTest() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field field = unsafeClass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        final Unsafe unsafe = (Unsafe) field.get(null);
        Thread t = new Thread(() -> {
            System.out.println("thread start");
            unsafe.park(false, 0L);
            System.out.println("thread recover");
        });
        t.start();
        Thread.sleep(1000 * 5);
        System.out.println("invoke");
        unsafe.unpark(t);
    }

    Object lock1 = new Object();
    Object lock2 = new Object();

    /**
     * 测试synchronized死锁
     * @throws InterruptedException
     */
    public void testDeadLockOfSynchronized() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            synchronized (lock1) {

                try {
                    System.out.println(Thread.currentThread().getId() + " enter lock1");
                    Thread.sleep(10);
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getId() + " enter lock2");
                        System.out.println(Thread.currentThread().getId() + " exit lock2");
                    }
                    System.out.println(Thread.currentThread().getId() + " exit lock1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            synchronized (lock2) {

                try {
                    System.out.println(Thread.currentThread().getId() + " enter lock2");
                    Thread.sleep(10);
                    synchronized (lock1) {
                        System.out.println(Thread.currentThread().getId() + " enter lock1");
                        System.out.println(Thread.currentThread().getId() + " exit lock1");
                    }
                    System.out.println(Thread.currentThread().getId() + " exit lock2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();
    }

    /**
     * 测试LockSupport
     *
     * @throws InterruptedException
     */
    public void testLockSupport() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getId() + " enter:" + System.currentTimeMillis());
            //LockSupport.parkUntil(System.currentTimeMillis() + 2000);
            LockSupport.parkNanos(1000 * 1000 * 1000);
            System.out.println(Thread.currentThread().getId() + " exit :" + System.currentTimeMillis());
        });
        t.start();
        t.join();
        List list = new ArrayList();
    }

}
