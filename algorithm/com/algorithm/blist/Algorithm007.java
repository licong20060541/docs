package com.algorithm.blist;

/**
 * set Matrix zero
 * 将包含0的行或列置0
 *
 * Given a m × n matrix, if an element is 0, set its entire row and column to 0.
 Do it in place. Follow up: Did you use extra space?
 A straight forward solution using O(mn) space is probably a bad idea.
 A simple improvement uses O(m + n) space, but still not the best solution.
 Could you devise a constant space solution?
 */
public class Algorithm007 {

    public static void main(String args[]) {
//        method1();
        method2();
    }

    /**
     * 取出行或列的0，然后统一置0
     * 时间复杂度O(m*n) 空间复杂度O(m+n)
     */
    private static void method1() {
        int m = 4;
        int n = 4;
        int[][] matrix = new int[][]{
                {1, 2, 3, 4},
                {0, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 0},
        };

        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];

        // 取出0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        // 置0操作
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j] ) {
                    matrix[i][j] = 0;
                }
            }
        }

        // print
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("" + matrix[i][j]);
            }
            System.out.println();
        }

    }

    /**
     * 利用第一行或第一列空间
     * 时间复杂度O(m*n) 空间复杂度O(1)
     */
    private static void method2() {
        int m = 4;
        int n = 4;

        int[][] matrix = new int[][]{
                {1, 2, 3, 4},
                {0, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 0},
        };

        boolean row_has_zero = false; // 第0行
        boolean col_has_zero = false; //

        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                row_has_zero = true;
                break;
            }
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                col_has_zero = true;
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++)
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++)
                if (matrix[i][0] == 0 || matrix[0][j] == 0)
                    matrix[i][j] = 0;
        }

        if (row_has_zero)
            for (int i = 0; i < n; i++)
                matrix[0][i] = 0;

        if (col_has_zero)
            for (int i = 0; i < m; i++)
                matrix[i][0] = 0;

        // print
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("" + matrix[i][j]);
            }
            System.out.println();
        }

    }

}
