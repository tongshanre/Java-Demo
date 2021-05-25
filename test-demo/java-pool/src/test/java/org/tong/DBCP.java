package org.tong;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBCP {
    static AtomicInteger num = new AtomicInteger(1);

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(DBCP.class.getClassLoader().getResourceAsStream("mysql.properties"));
        properties.put("initialSize", 2);
        BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        for (int i = 0; i < 8; i++) {
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
        System.out.println("waiting.....");
        new CountDownLatch(1).await();
    }
}
