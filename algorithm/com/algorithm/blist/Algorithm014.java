package com.algorithm.blist;

/**
 * 判断数独是否有效
 * 时间复杂度O(n^2) 空间复杂度O(1)
 */
public class Algorithm014 {

    public static void main(String args[]) {

        boolean[] used = new boolean[9];
        int[][] nums = new int[][] {
                {1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
                ,{1, 1, 2, 2, 2, 3, 4, 4, 5}
        };

        for (int m = 0; m < 9; m++) {
            for (int i = 0; i < 9; i++) {
                used[i] = false;
            }
            for (int j = 0; j < 9; j++) { // 检查行
                if (!check(nums[m][j], used)) {
                    return;
                }
            }
            for (int i = 0; i < 9; i++) {
                used[i] = false;
            }
            for (int j = 0; j < 9; j++) { // 检查列
                if (!check(nums[j][m], used)) {
                    return;
                }
            }
        }

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                for (int i = 0; i < 9; i++) {
                    used[i] = false;
                }
                for (int i = r * 3; i < r * 3 + 3; ++i) {
                    for (int j = c * 3; j < c * 3 + 3; ++j) {
                        if (!check(nums[i][j], used)) {
                            return;
                        }
                    }
                }
            }
        }

        System.out.println("success~");
    }

    private static boolean check(int tmp, boolean[] used) {
        if (tmp == -1) {
            return true;
        }
        if (used[tmp]) {
            return false;
        }
        return used[tmp] = true;
    }

}
