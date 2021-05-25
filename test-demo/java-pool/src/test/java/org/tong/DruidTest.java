package org.tong;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DruidTest {

    static AtomicInteger num = new AtomicInteger(1);

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(DruidTest.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try (Connection connection = dataSource.getConnection();) {
                    System.out.println(num.getAndAdd(1) + "-----" + Thread.currentThread().getId() + "-" + connection);
                    System.out.println(connection.getClass());
                    Thread.sleep(1000 * 5);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("waiting...");
        new CountDownLatch(1).await();
    }
}
