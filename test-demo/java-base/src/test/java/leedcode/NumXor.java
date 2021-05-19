package leedcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NumXor {
    /**
     * 两个出现一次，其余出现两次
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        // 0. 参数校验
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int num1 = 0;
        int num2 = 0;
        // 1. 计算特征位
        int flag = Arrays.stream(nums).reduce(0, (a, b) -> a ^ b);
        int character = 1;
        while ((character & flag) == 0) {
            character = character << 1;
        }
        // 2. 分组
        for (int num : nums) {
            if ((num & character) == 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }

        return new int[]{num1, num2};
    }

    /**
     * 除一个数字外其余都出现三次
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        HashMap<String, Integer> map = new HashMap<>();
        Arrays.stream(nums).forEach(num -> {
            String key = num + "";
            if (!map.containsKey(key)) {
                map.put(key, 0);
            }
            map.put(key, map.get(key) + 1);
        });
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return Integer.valueOf(entry.getKey());
            }
        }
        return 0;
    }
}
