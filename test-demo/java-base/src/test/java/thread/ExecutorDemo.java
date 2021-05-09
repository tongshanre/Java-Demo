package thread;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ExecutorDemo {


    public void testExecutor() throws InterruptedException, IOException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(() -> {
        }, 19, TimeUnit.SECONDS);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                System.out.println("run");
                Thread.sleep(1000 * 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            System.out.println("run2");
        });
        Thread.sleep(1000);
        executorService.shutdown();
    }


}
