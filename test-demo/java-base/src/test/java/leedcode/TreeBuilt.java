package leedcode;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilt {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0) {
            return null;
        }
        List<Integer> preList = new ArrayList();
        List<Integer> inoList = new ArrayList();
        for (int i = 0; i < preorder.length; i++) {
            preList.add(preorder[i]);
            inoList.add(inorder[i]);
        }
        return build(preList, inoList);

    }

    public TreeNode build(List<Integer> preorder, List<Integer> inorder) {
        if (preorder.size() == 0) {
            return null;
        }
        //根
        int root = preorder.get(0);
        TreeNode left = null, right = null;
        if (preorder.size() > 1) {
            int pos = searchIndex(inorder, root);
            //左
            List<Integer> li = inorder.subList(0, pos);
            List<Integer> lp = preorder.subList(1, li.size() + 1);
            left = build(lp, li);
            //右
            List<Integer> ri = inorder.subList(pos + 1, inorder.size());
            List<Integer> rp = preorder.subList(lp.size() + 1, preorder.size());
            right = build(rp, ri);
        }
        TreeNode treeNode = new TreeNode(root);
        treeNode.left = left;
        treeNode.right = right;
        return treeNode;
    }

    // 查询指定元素的位置
    int searchIndex(List<Integer> nums, int num) {
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == num)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] pre = {3, 9, 20, 15, 7};
        int[] ino = {9, 3, 15, 20, 7};
        new TreeBuilt().buildTree(pre, ino);

    }
}
