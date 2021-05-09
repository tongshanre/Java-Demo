package leedcode;

import java.math.BigInteger;

public class Fiber {
    public int fib(int n) {
        if (n <= 0) {
            return 0;
        } else if (n <= 2) {
            return 1;
        }
        BigInteger x = new BigInteger("1", 10);
        BigInteger y = new BigInteger("1", 10);
        do {
            BigInteger tmp1 = x.add(y);
            x = y;
            y = tmp1;
            n--;
        } while (n > 2);
        System.out.println(y);
        System.out.println(y.mod(new BigInteger("1000000007", 10)));
        return y.mod(new BigInteger("1000000007", 10)).intValue();
    }

    public static void main(String[] args) {
        System.out.println(new Fiber().fib(95));
    }
}
