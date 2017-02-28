package com.algorithm.alist;

import java.util.HashMap;

/**
 * 求三数和
 * 时间复杂度O(n^2) 空间复杂度O(1)
 */
public class Algorithm008 {

    public static void main(String args[]) {

        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        int SUM = 0;

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
