package io;

import org.junit.Test;

public class StringTest {
    /**
     * 测试字符串拼接效率
     */
    public void testStringConcat() {
        final int MAX_COUNT = 100000;
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < MAX_COUNT; i++) {
            str += i;
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("normal concat use time:%d, length=%d", end - start, str.length()));

        //
        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_COUNT; i++) {
            sb.append(i);
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("stringbuilder concat use time:%d, length=%d", end - start, sb.length()));
    }

    @Test
    public void testStringIntern() {
        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = str1.substring(0,1);
        System.out.println(str1.hashCode()+" "+str2.hashCode()+" "+str3.hashCode());
        System.out.println(str1 == str2);
        System.out.println("abc" == str1.intern());
        System.out.println("abc" == str2.intern());
        System.out.println(str1.intern() == str2.intern());
        System.out.println(System.identityHashCode(str1) + "   "+System.identityHashCode(str2));
    }
}
