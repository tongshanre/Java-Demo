package leedcode;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Step {
    public int numWays(int n) {
        if (n <= 0) {
            return 1;
        }
        if (n < 2) {
            return n;
        }
        java.math.BigInteger[] steps = new java.math.BigInteger[n];
        steps[0] = (new java.math.BigInteger("1", 10));
        steps[1] = (new java.math.BigInteger("2", 10));
        for (int i = 2; i < n; i++) {
            System.out.println(i);
            steps[i] = steps[i - 2].add(steps[i - 1]);
        }
        return steps[steps.length - 1].mod(new BigInteger("1000000007", 10)).intValue();
    }

    public String removeDuplicates(String S) {
        List<Character> chars = new ArrayList<>();
        for (char c : S.toCharArray()) {
            chars.add(c);
        }
        int count = 0;
        do {
            List<Character> list = new ArrayList<>();
            count = 0;
            for (char c : chars) {//扫描每一个字符
                if (list.size() > 0 && list.get(list.size() - 1) == c) {
                    list.remove(list.size() - 1);
                    count++;
                } else {
                    list.add(c);
                }
            }
            chars = list;
        } while (count == 0);
        StringBuffer sb = new StringBuffer();
        chars.stream().forEach(c -> {
            sb.append(c);
        });
        return sb.toString();
    }

    public int hammingWeight(int n) {
        long num = Integer.toUnsignedLong(n);
        int count = 0;
        while (num != 0 && (n % 2 == 1)) {
            count++;
            num = num >> 1;
        }
        return count;
    }

    static volatile int num = 0;

    public boolean verifyPostorder(int[] postorder) {
        if (postorder.length <= 1) {
            return true;
        }
        return verifyPostorder2(postorder, 0, postorder.length - 1);
    }

    // 闭区间
    public boolean verifyPostorder2(int[] postorder, int from, int to) {

        //边界条件
        if (from >= to) {
            return true;
        }
        //1. 取得最后一个元素为根
        int root = postorder[to];
        //2. 找到根在后续列表中的位置：最小、中间、最大三种情况，需要判断是否合法（右边的元素都比根大）
        int index = findRootIndex(postorder, from, to);
        System.out.println(String.format("from:%d, to:%d, index:%d", from, to, index));
        //3. 根据根的位置拆分数组
        if (index < from) { // 所有的都大
            if (!checkValid(postorder, from, to)) {
                return false;
            }
            return verifyPostorder2(postorder, from, to - 1);
        } else {
            if (!checkValid(postorder, index + 1, to)) {
                return false;
            }

            boolean left = verifyPostorder2(postorder, from, index);
            boolean right = verifyPostorder2(postorder, index + 1, to - 1);
            return left & right;
        }
    }

    // 找到root的位置：比root小的最后一个位置，from-1， to
    public int findRootIndex(int[] postorder, int from, int to) {
        for (int i = from; i <= to - 1; i++) {
            if (postorder[i] > postorder[to]) {
                return i - 1;
            }
        }
        return to - 1;
    }

    public boolean checkValid(int[] postorder, int from, int to) {
        for (int i = from; i < to; i++) {
            if (postorder[i] < postorder[to]) {
                return false;
            }
        }
        return true;
    }

    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.push(root);
        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()) {//非空
            TreeNode node = queue.pop();
            if (node == null)
                sb.append("null");
            else {
                sb.append(node.val);
                queue.push(node.left);
                queue.push(node.right);
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return "[" + sb.toString() + "]";
    }

    // index 开始于1
    List<Integer> nullPos = new ArrayList<Integer>();

    public TreeNode deserialize(String[] data, int index) {
        //1. 是否需要修正位置
        //

        TreeNode node = new TreeNode(Integer.valueOf(1));
        node.left = deserialize(data, index * 2);
        node.right = deserialize(data, index * 2 + 1);
        return node;
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        Arrays.stream(Step.class.getMethods()).forEach(method -> {
            System.out.print(method.getName() + ":\t");
            Arrays.stream(method.getParameters()).forEach(name -> {
                System.out.print(name.getName() + ",");
            });
            System.out.println();
        });


    }

}

