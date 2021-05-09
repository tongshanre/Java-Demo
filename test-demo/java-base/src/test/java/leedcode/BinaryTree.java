package leedcode;

import java.util.*;

/**
 * 二叉树操作
 */
public class BinaryTree {

    // 查找最小公共节点
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }

    public boolean search(TreeNode root, TreeNode p, List<TreeNode> path) {
        if (root == null) {
            return false;
        }
        path.add(root);
        if (root.val == p.val) {
            return true;
        }
        //左子
        if (search(root.left, p, path)) {
            return true;
        } else {
            path.remove(path.size() - 1);
        }
        //右子
        if (search(root.right, p, path)) {
            return true;
        } else {
            path.remove(path.size() - 1);
        }
        return false;
    }

    public static void test(List list) {
        System.out.println("-" + System.identityHashCode(list));
        list = new ArrayList();
        System.out.println("-" + System.identityHashCode(list));

    }

    public static void main(String[] args) {
        String s = "";

    }
}
