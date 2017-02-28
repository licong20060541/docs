package com.algorithm.alist;

/**
 * 移除重复数字(排好序),但允许相邻两个相同
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm002 {

    public static void main(String args[]) {

//        method1();
        method2();

    }

    private static void method1() {
        int[] nums = new int[] {1, 1, 1, 2, 2, 2, 3, 4, 4, 5, 5, 5};

        int index = 1;

        for (int i=2; i<nums.length; i++) {
            if(nums[index - 1] != nums[i]) {
                nums[++index] = nums[i];
            }
        }

        System.out.println("result: ");
        for(int i=0; i<=index; i++) {
            System.out.println("" + nums[i]);
        }
    }

    private static void method2() {

        int[] nums = new int[] {1, 1, 1, 2, 2, 2, 3, 4, 4, 5, 5, 5};

        int index = 0;

        for (int i=1; i<nums.length-1; i++) {
            if(nums[i-1] == nums[i] && nums[i+1] == nums[i]) {
                continue;
            }
            nums[++index] = nums[i];
        }

        System.out.println("result: ");
        for(int i=0; i<=index; i++) {
            System.out.println("" + nums[i]);
        }

    }

//    result:
//            1
//            1
//            2
//            2
//            3
//            4
//            4
//            5

}
