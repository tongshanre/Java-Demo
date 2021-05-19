package leedcode;

import java.util.Arrays;

public class Dice {
    public double[] dicesProbability(int n) {
        //1. 边界检查
        if (n <= 0) {
            return new double[0];
        }
        // 2. 二维dp数组
        int dp[][] = new int[n][6 * n];
        // 2.1 初始化边界
        for (int i = 0; i < 6; i++)
            dp[0][i] = 1;

        // 3. 迭代赋值, 投第i个骰子
        for (int i = 1; i < n; i++) {
            // 当前轮的取值范围
            for (int j = i; j < 6 * (i + 1); j++) {
                for (int x = 1; x <= 6; x++) {
                    if ((j - x) < 0) {
                        break;
                    }
                    dp[i][j] += dp[i - 1][j - x];
                }
            }
        }
        // 4. 封装结果
        double total = Math.pow(6, n);
        double[] result = new double[6 * n - n + 1];
        for (int i = n - 1; i < 6 * n; i++) {
            result[i - n + 1] = dp[n - 1][i] / total;
        }
        return result;
    }

    public static void main(String[] args) {
        Arrays.stream(new Dice().dicesProbability(3)).forEach(item -> {
            System.out.println(item);
        });

    }
}
