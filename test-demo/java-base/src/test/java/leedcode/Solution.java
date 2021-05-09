package leedcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        return findRectangle(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1, target);
    }

    // 在(r1,c1)-(r2,c2)范围内查询target
    public boolean findRectangle(int[][] matrix, int r1, int r2, int c1, int c2, int target) {
        System.out.println();
        System.out.println(String.format("(%d, %d)-(%d,%d)", r1, c1, r2, c2));
        // 搜索行,缩减c2
        int cpos = rowSearch(matrix, r1, c1, c2, target);
        if (cpos == -1) { // 当前行没有，即全部没有
            return false;
        }
        int val = matrix[r1][cpos];
        if (val == target) return true;
        else c2 = cpos;
        // 搜索列,缩减r2
        int rpos = colSearch(matrix, c2, r1, r2, target);
        if (rpos == -1) {
            c2--;
            if (c2 < 0) {
                return false;
            }
        } else {
            val = matrix[rpos][c2];
            if (val == target) {
                return true;
            } else {
                c2--;
            }
        }
        if (c2 < 0) {
            return false;
        }
        return findRectangle(matrix, r1, r2, c1, c2, target);
    }

    // 二分查询当前行是有target
    // 有：返回坐标
    // 无：返回小于target的最临近的值或-1
    public int rowSearch(int[][] matrix, int row, int c1, int c2, int target) {
        if (c1 == c2 && matrix[row][c1] == target) {
            return c1;
        }
        while (c1 < c2) {
            int mid = (c1 + c2) / 2;
            int val = matrix[row][mid];
            if (val == target) {
                return mid;
            } else if (target > val) {
                c1 = mid + 1;
            } else {
                c2 = mid;
            }
        }
        return c1;
    }

    // 按列查询当前列中是否有target
    public int colSearch(int[][] matrix, int col, int r1, int r2, int target) {
        if (r1 == r2 && matrix[r1][col] == target) {
            return r1;
        }
        while (r1 < r2) {
            int mid = (r1 + r2) / 2;
            int val = matrix[mid][col];
            if (val == target) {
                return mid;
            } else if (target > val) {
                r1 = mid + 1;
            } else {
                r2 = mid;
            }
        }
        return r1;
    }


    public static void main(String[] args) {
        /*int[][] matrix = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};*/
        int[][] matrix = {{1, 1}};
        boolean pos = new Solution().findNumberIn2DArray(matrix, 2);
        String s = "";
        int []a = new int[0];
    }

}
