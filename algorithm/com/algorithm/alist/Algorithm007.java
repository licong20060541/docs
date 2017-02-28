package com.algorithm.alist;

import java.util.HashMap;

/**
 * 求两数和
 * 时间复杂度O(n) 空间复杂度O(n)
 */
public class Algorithm007 {

    public static void main(String args[]) {

        int[] nums = new int[]{1, 1, 2, 2, 2, 3, 4, 4, 5};
        int SUM = 9;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        int gap;

        for (int i = 0; i < nums.length; i++) {
            gap = SUM - nums[i];
            if (map.containsKey(gap)) {
                System.out.println("result: " + nums[i] + " index: " + i);
                System.out.println("result: " + gap + " index: " + map.get(gap));
                break;
            }
        }

//        result: 4 index: 6
//        result: 5 index: 8

    }

}
