package jmm;

import java.io.IOException;

public class HeapTest {

    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        User u = new User();
        u.id = 5;
        u.name = "test";
    }


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start));
        System.in.read();
    }
}
