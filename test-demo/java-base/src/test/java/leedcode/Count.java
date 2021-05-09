package leedcode;

import org.junit.Test;

import java.util.*;

public class Count {

    public int numOfN(int n) {
        if (n == 1) {
            return 1;
        } else {
            return (int) (Math.pow(10, n - 1) - numOfN(n - 1));
        }
    }


    @Test
    public void test() {
        System.out.println(new Integer(1) == new Integer(1));
        Integer a;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(new Integer(1));
        queue.addLast(new Integer(2));
        System.out.println(queue);
        System.out.println(queue);
        queue.peekLast();
    }

    LinkedList<Integer> queue = null;

    public int max_value() {
        if (queue.size() == 0) {
            return -1;
        }
        return queue.pollFirst();
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<Integer>();
        nums.add(3);
        nums.add(2);
        nums.add(1);
        Collections.sort(nums, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(nums);
    }
}
