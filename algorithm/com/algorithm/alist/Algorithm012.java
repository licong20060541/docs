package com.algorithm.alist;

/**
 * 找到下个较大的数字,如果找不到则排列为最小
 * 如  1 2 3 -- 1 3 2
 *    3 2 1 -- 1 2 3
 *    1 1 5 -- 1 5 1
 * 时间复杂度O(n) 空间复杂度O(1)
 *
 * 算法：
 * eg:    6 8 7 4 3 2
 * step1: find 6 -- 从右至左找到第一个不是递增的数字
 * step2: find 7 -- 从右至左找到第一个比step1大的数字
 * step3: 7 8 6 4 3 2 -- 交换
 * step4: 7 2 3 4 6 8 -- 对step1数字的位置后的进行排序，即第一个数字后的排序
 *
 */
public class Algorithm012 {

    public static void main(String args[]) {

        int[] nums = new int[] {6, 8, 7, 4, 3, 2};
//        int[] nums = new int[] {8, 7, 6, 4, 3, 2};
        int firstIndex = -1;
        int secondIndex = -1;

        // step1
        for( int i = nums.length - 1; i > 0; i--) {
            if(nums[i - 1] < nums[i]) {
                firstIndex = i - 1;
                break;
            }
        }
        if(firstIndex != -1) {
            // step2
            for( int i = nums.length - 1; i > firstIndex; i--) {
                if(nums[i] > nums[firstIndex]) {
                    secondIndex = i;
                    break;
                }
            }

            // step3
            int tmp = nums[firstIndex];
            nums[firstIndex] = nums[secondIndex];
            nums[secondIndex] = tmp;

            // step4
            reverse(nums, firstIndex + 1);

        } else {
            // not found, just reverse
            reverse(nums, 0);
        }
        for( int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

    private static void reverse(int[] srcDatas, int fromIndex) {
        int length = srcDatas.length;
        // 设from为0，则length为6或者7时，mid都是2，合理的值
        int mid = (fromIndex + length) / 2 - 1;
        int j = 1;
        for (int i = fromIndex; i <= mid; i++, j++) {
            int tmp = srcDatas[i];
            srcDatas[i] = srcDatas[length - j];
            srcDatas[length - j] = tmp;
        }
    }

}
