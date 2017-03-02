package com.algorithm.blist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数组加法
 * 时间复杂度O(n) 空间复杂度O(1)
 * 如：[9, 9, 8, 6] + 14 = 10000
 */
public class Algorithm004 {

    public static void main(String args[]) {

        Integer[] nums = new Integer[]{9, 9, 8, 6};
        int digit = 100014;

        int sum;

        for (int i = nums.length - 1; i >= 0; i--) {
            sum = nums[i] + digit;
            nums[i] = sum % 10;
            digit = sum / 10;
        }
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, nums);
        if (digit > 0) {
            while (digit > 10) {
                list.add(0, digit % 10);
                digit = digit / 10;
            }
            list.add(0, digit);
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print("--" + list.get(i));
        }

    }

    // --1--1--0--0--0--0

}
