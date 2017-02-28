package com.algorithm.alist;

/**
 * 队列(排好序)进行旋转操作，然后查找, 允许条件重复
 * 如 【1 3 1 1 1】，则上个方法失效
 * 时间复杂度O(log n) 空间复杂度O(1)
 */
public class Algorithm004 {

    public static void main(String args[]) {

        int[] nums = new int[] {4, 5, 4, 4, 4, 4, 4};
        int NUM_TO_FIND = 5;

        int first = 0;
        int last = nums.length - 1;
        int mid;

        while(first <= last) {
            mid = (first + last) / 2;
            if(nums[mid] == NUM_TO_FIND) {
                System.out.println("result: " + mid);
                break;
            }
            if(nums[first] < nums[mid]) {
                if(nums[first] <= NUM_TO_FIND && NUM_TO_FIND < nums[mid]) {
                    last = mid - 1; // 对应 while(first <= last)
                } else {
                    first = mid + 1;
                }
            } else if(nums[first] > nums[mid]) { // !!!注意条件
                if(nums[mid] < NUM_TO_FIND && NUM_TO_FIND <= nums[last]) {
                    first = mid + 1;
                } else {
                    last = mid - 1;
                }
            } else {
                first++;
            }
        }
        System.out.print("over");
    }
}
