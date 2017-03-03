package com.algorithm.blist;

/**
 * single number
 * Given an array of integers, every element appears twice except for one.
 * Find that single one.
 * Note: Your algorithm should have a linear runtime complexity.
 * Could you implement it without usingextra memory?
 * 算法：异或，不仅能处理2次，偶数此均可
 */
public class Algorithm010 {

    public static void main(String args[]) {
        int[] nums = new int[] {2, 3, 5, 7, 2, 5, 3};
        int x = 0;
        for (int i : nums) {
            x ^= i;
        }
        System.out.print("" + x);
    }


}
