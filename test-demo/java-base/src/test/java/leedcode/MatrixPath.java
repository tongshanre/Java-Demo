package leedcode;

import java.util.concurrent.FutureTask;

public class MatrixPath {
    boolean[][] flag;

    public boolean exist(char[][] board, String word) {
        if (board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        //1. 访问标记数组
        flag = new boolean[board.length][board[0].length];
        //2. 启动
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    flag[i][j] = true;
                    if (match(board, i, j, word, 1)) {
                        return true;
                    }
                    flag[i][j] = false;
                }
            }
        }
        return false;
    }

    // 递归判断是否包含word,每一次判断一个字符
    public boolean match(char[][] board, int x, int y, String word, int index) {
        System.out.println(String.format("(%d,%d)->%d", x, y, index));
        // 1. 递归截止条件
        if (word.length() == index) {
            return true;
        }
        boolean up = false, down = false, left = false, right = false;
        // 2.1 上
        if (x - 1 >= 0 && flag[x - 1][y] == false && board[x - 1][y] == word.charAt(index)) {
            flag[x - 1][y] = true;
            up = match(board, x - 1, y, word, index + 1);
            if (up) {
                return true;
            }
            flag[x - 1][y] = false;
        }
        // 2.2 下
        if (x + 1 < board.length && flag[x + 1][y] == false && board[x + 1][y] == word.charAt(index)) {
            flag[x + 1][y] = true;
            down = match(board, x + 1, y, word, index + 1);
            if (down) {
                return true;
            }
            flag[x + 1][y] = false;
        }
        // 2.3 左
        if (y - 1 >= 0 && flag[x][y - 1] == false && board[x][y - 1] == word.charAt(index)) {
            flag[x][y - 1] = true;
            left = match(board, x, y - 1, word, index + 1);
            if (left) {
                return true;
            }
            flag[x][y - 1] = false;
        }
        // 2.4 右
        if (y + 1 < board[0].length && flag[x][y + 1] == false && board[x][y + 1] == word.charAt(index)) {
            flag[x][y + 1] = true;
            right = match(board, x, y + 1, word, index + 1);
            if (right) {
                return true;
            }
            flag[x][y + 1] = false;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}