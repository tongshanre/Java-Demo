package leedcode;

public class Reverse {
    public int reversePairs(int[] nums) {
        // 1. 参数校验
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 2. 遍历求解
        int total = 0;
        int i = 0;
        int j = 1;
        while (i < nums.length && j < nums.length) {
            while (j < nums.length) {
                if (nums[j] < nums[i]) {
                    total++;
                }
                j++;
            }
            i++;
            j = i + 1;
        }
        return total;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{7,5,6,4};
        System.out.println(new Reverse().reversePairs(nums));
    }
}
