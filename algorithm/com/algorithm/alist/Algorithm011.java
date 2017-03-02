package com.algorithm.alist;

/**
 * 移除数字
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm011 {

    public static void main(String args[]) {

        int[] nums = new int[]{1, 1, 2, 2, 2, 3, 4, 4, 5};

        int TARGET = 2;
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            if (TARGET != nums[i]) {
                nums[index++] = nums[i];
            }
        }

        System.out.println("result: ");
        for (int i = 0; i <= index; i++) {
            System.out.println("" + nums[i]);
        }
    }

//    result:
//            1
//            1
//            3
//            4
//            4
//            5
//            4

}
