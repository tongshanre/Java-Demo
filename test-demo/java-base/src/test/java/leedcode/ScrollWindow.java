package leedcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ScrollWindow {

    /**
     * 求滑动窗口的最大值
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length <= k) {
            return new int[]{Arrays.stream(nums).max().getAsInt()};
        }
        LinkedList<Integer> maxQueue = new LinkedList<Integer>();
        List<Integer> windowList = new ArrayList<Integer>();
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < k; i++) {
            Integer item = nums[i];
            windowList.add(item);
            offerLast(maxQueue, item);
        }
        result.add(maxQueue.peekFirst());
        for (int i = k; i < nums.length; i++) {
            Integer item = windowList.remove(0);
            removeFirst(maxQueue, item);
            item = new Integer(nums[i]);
            offerLast(maxQueue, item);
            windowList.add(item);
            result.add(maxQueue.peekFirst());
        }
        int[] values = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            values[i] = result.get(i);
        }
        return values;
    }

    // 添加元素
    protected void offerLast(LinkedList<Integer> maxQueue, Integer item) {
        while (!maxQueue.isEmpty() && maxQueue.peekLast() < item) {
            maxQueue.removeLast();
        }
        maxQueue.addLast(item);
    }

    // 移除元素
    protected void removeFirst(LinkedList<Integer> maxQueue, Integer item) {
        if (maxQueue.peekFirst() == item) {

            maxQueue.removeFirst();
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(new ScrollWindow().maxSlidingWindow(nums, 3));
    }
}
