package leedcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SerializeDeserialize {


    /**
     * 将序列化结构格式化为完整结构，已去除中括号
     *
     * @param data
     * @return
     */
    public List<Integer> expendData(String data) {
        List<Integer> list = new ArrayList<>();
        for (String item : data.trim().split(",")) {
            list.add(item.equals("null") ? null : Integer.valueOf(item));
        }
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i - 1) == null) {
                if (i * 2 < list.size()) {
                    list.add(i * 2, null);
                }
                if ((i * 2 + 1) < list.size()) {
                    list.add(i * 2 + 1, null);
                }
            }
        }
        System.out.println("expendData:" + list);
        return list;
    }

    //序列化
    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        List<String> items = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        //1. 加入根节点
        queue.addLast(root);
        while (queue.size() > 0) {
            TreeNode node = queue.removeFirst();
            if (node != null) {
                items.add(node.val + "");
                queue.addLast(node.left);
                queue.addLast(node.right);
            } else {
                items.add("null");
            }
        }
        for (int j = items.size() - 1; j >= 0; j--) {
            if (items.get(j).equals("null")) {
                items.remove(j);
            } else {
                break;
            }
        }
        return "[" + String.join(",", items.toArray(new String[0])) + "]";
    }
}


