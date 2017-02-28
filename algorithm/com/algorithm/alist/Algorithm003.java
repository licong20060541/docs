package com.algorithm.alist;

/**
 * 队列(排好序)进行旋转操作，然后查找，元素不重复情况
 * 时间复杂度O(log n) 空间复杂度O(1)
 */
public class Algorithm003 {

    public static void main(String args[]) {

        int[] nums = new int[] {4, 5, 6, 7, 0, 1, 2, 3};
//        int[] nums = new int[] {4, 5};
        int NUM_TO_FIND = 0;

        int first = 0;
        int last = nums.length - 1;
        int mid;

        while(first <= last) {
            mid = (first + last) / 2;
            if(nums[mid] == NUM_TO_FIND) {
                System.out.println("result: " + mid);
                break;
            }
            if(nums[first] <= nums[mid]) {
                if(nums[first] <= NUM_TO_FIND && NUM_TO_FIND < nums[mid]) {
                    last = mid - 1; // 对应 while(first <= last)
                } else {
                    first = mid + 1;
                }
            } else {
                if(nums[mid] < NUM_TO_FIND && NUM_TO_FIND <= nums[last]) {
                    first = mid + 1;
                } else {
                    last = mid - 1;
                }
            }
        }
        System.out.print("over");
    }
}
