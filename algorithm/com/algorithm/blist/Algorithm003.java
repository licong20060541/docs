package com.algorithm.blist;

/**
 * 旋转矩阵
 * 时间复杂度O(n^2) 空间复杂度O(1)
 */
public class Algorithm003 {

    public static void main(String args[]) {

//        method1();
        method2();

    }

    private static void method1() {

        int[][] matrix = new int[][]{{1, 2}, {3, 4}};
        int length = 2;

        // 沿着副对角线旋转
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                swap(i, j, length - 1 - j, length - 1 - i, matrix);
            }
        }

        // 沿着水平中线旋转
        for (int i = 0; i < length / 2; i++) {
            for (int j = 0; j < length; j++) {
                swap(i, j, length - 1 - i, j, matrix);
            }
        }

        // print
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
        }
        // 3, 1, 4, 2,
    }


    private static void method2() {

        int[][] matrix = new int[][]{{1, 2}, {3, 4}};
        int length = 2;

        // 沿着水平中线旋转
        for (int i = 0; i < length / 2; i++) {
            for (int j = 0; j < length; j++) {
                swap(i, j, length - 1 - i, j, matrix);
            }
        }

        // 沿着主对角线旋转 // ！！！主主
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                swap(i, j, j, i, matrix);
            }
        }

        // print
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
        }
    }


    private static void swap(int index1, int index2, int index3, int index4, int[][] matrix) {
        int tmp = matrix[index1][index2];
        matrix[index1][index2] = matrix[index3][index4];
        matrix[index3][index4] = tmp;
    }

}
