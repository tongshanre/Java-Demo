package leedcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class UglyNum {

    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Boolean> map = new HashMap<>();
        int len = 0, max_len = 0;
        char[] chars = s.toCharArray();
        int i = 0, j = 0;
        while (i < s.length() && j < s.length()) {
            char c = chars[j];
            if (map.containsKey(c)) {//已包含该字符
                map.remove(chars[i]);
                i++;
                len--;
            } else {
                map.put(c, false);
                j++;
                len++;
                if (len > max_len) {
                    max_len = len;
                }
            }
        }
        return max_len;
    }

    public int maxValue(int[][] grid) {
        return maxValue(grid, grid.length, grid[0].length);
    }

    private int dp(int[][] values) {
        // 1. 初始化dp数组
        int[][] dp = new int[values.length][values[0].length];
        // 2. 迭代赋值
        for (int row = 0; row < values.length; row++) {//行
            for (int col = 0; col < values[0].length; col++) {//列
                if (col == 0 && row == 0) {
                    dp[row][col] = values[0][0];
                }
                int up = row - 1 >= 0 ? dp[row - 1][col] : 0;
                int left = col - 1 >= 0 ? dp[row][col - 1] : 0;
                int cur = values[row][col];
                dp[row][col] = Math.max(up + cur, left + cur);
            }
        }
        // 3. 返回结果
        return dp[values.length - 1][values[0].length - 1];
    }

    private int maxValue(int[][] values, int r, int c) {
        if (r == 0 && c == 0) {
            return values[0][0];
        } else if (r < 0 || c < 0) {
            return 0;
        }
        int upValue = maxValue(values, r - 1, c);
        int leftVaue = maxValue(values, r, c - 1);
        int cur_value = values[r][c];
        return Math.max(upValue + cur_value, leftVaue + cur_value);

    }

    /*public int findNthDigit(int n) {
        int num = 0;
        int length = 0;
        while (length <= n) {
            length += (num + "").length();
            num++;
        }
        String str = (--num + "");
        int index = str.length() - length + n;
        return Integer.valueOf(str.substring(index, index + 1));
    }
*/
    public int findNthDigit(int n) {
        if (n < 10) {
            return n;
        }
        // 1. 从1开始
        n -= 1;

        // 2.
        int start = 100;
        while (n >= start) {
            if (n <= start * 10) {
                break;
            }
            start *= 10;
        }

        return 0;
    }

    public static void main(String[] args) {
        int nums[] = {3, 5, 9, 30, 34};
        java.util.List<String> list = new ArrayList<String>();
        for (int num : nums) {
            list.add(num + "");
        }
        /**
         * 排序规则：
         *  相同长度的直接按大小进行排序；
         *  不同长度的按首字母排序，首字母相同往后比较
         */
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String num1 = o1;
                String num2 = o2;
                if (num1.length() == num2.length()) { //长度相同
                    return o1.compareTo(o2);
                } else {    // 长度不同
                    int diff = num1.length() - num2.length();
                    if (diff > 0) {
                        while (diff-- > 0) {
                            num2 += num2.charAt(num2.length() - 1);
                        }
                    } else {
                        while (diff-- > 0) {
                            num1 += num1.charAt(num1.length() - 1);
                        }
                    }
                    return num1.compareTo(num2);
                }
            }
        });
        System.out.println(list);
        System.out.println(String.join("", list));
    }
}
