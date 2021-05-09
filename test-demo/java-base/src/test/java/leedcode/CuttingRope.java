package leedcode;

import java.math.BigInteger;

public class CuttingRope {

    public int cuttingRope(int n) {
        BigInteger[] dp = new BigInteger[n + 1];
        dp[1] = new BigInteger("1", 10);
        dp[2] = new BigInteger("2", 10);
        dp[3] = new BigInteger("3", 10);
        for (int i = 4; i <= n; i++) {
            BigInteger max = new BigInteger("0", 10);
            for (int j = 1; j <= i / 2; j++) {
                if (max.compareTo(dp[j].multiply(dp[i - j])) < 0) {
                    max = dp[j].multiply(dp[i - j]);
                }
            }
            dp[i] = max;
        }
        return dp[n].mod(new BigInteger("1000000007", 10)).intValue();
    }

    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (x == 1) {
            return x;
        }
        double result = x;
        long times = Math.abs((long) n);
        while (times > 1) {
            result = result * x;
            times--;
        }
        if (n > 0) {
            return result;
        } else {
            return 1 / result;
        }
    }

    public static void main(String[] args) {
        System.out.println(new CuttingRope().myPow(2, -2147483648));
    }
}
