package leedcode;

import java.util.LinkedList;

public class GFS {
    public int movingCount(int m, int n, int k) {
        if (m <= 0 || n <= 0 || k < 0) {
            return 0;
        }
        boolean flag[][] = new boolean[m][n];
        LinkedList<Point> queue = new LinkedList<Point>();
        int x = 0, y = 0, count = 0;
        queue.add(new Point(0, 0));
        while (!queue.isEmpty()) {
            Point point = queue.removeFirst();
            if (!flag[point.x][point.y] && check(point, k)) {//没访问过
                flag[point.x][point.y] = true;
                //添加临近节点
                //1. 上
                if (point.x - 1 >= 0) {
                    queue.add(new Point(point.x - 1, point.y));
                }
                //2. 下
                if (point.x + 1 < m) {
                    queue.add(new Point(point.x + 1, point.y));
                }
                //3. 左
                if (point.y - 1 >= 0) {
                    queue.add(new Point(point.x, point.y - 1));
                }
                //4. 右
                if (point.y + 1 < n) {
                    queue.add(new Point(point.x, point.y + 1));
                }
                count++;
            }
        }
        return count;
    }

    public boolean check(Point point, int k) {
        int x = point.x;
        int y = point.y;
        int count = 0;
        while (x != 0) {
            count += x % 10;
            x = x / 10;
        }
        while (y != 0) {
            count += y % 10;
            y = y / 10;
        }
        if (count <= k) {
            return true;
        } else {
            return false;
        }
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        System.out.println(list);
        list.removeFirst();
        System.out.println(list);
    }
}
