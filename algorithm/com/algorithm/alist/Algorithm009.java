package com.algorithm.alist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 最接近的三个数
 * 时间复杂度O(n^2) 空间复杂度O(1)
 */
public class Algorithm009 {

    public static void main(String args[]) {

        Integer[] nums = new Integer[]{-1, 2, 1, -4};

        int FIND = 1;
        int result;
        int minGap = Integer.MAX_VALUE;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);

        // 2, 夹逼
        for (int i = 0; i < aList.size() - 2; i++) {
            int j = i + 1;
            int k = aList.size() - 1;
            while (j < k) {
                final int sum = aList.get(i) + aList.get(j) + aList.get(k);
                final int gap = Math.abs(sum - FIND);
                if(gap < minGap) {
                    minGap = gap;
                    result = sum;
                    System.out.println("===================");
                    System.out.println("result: " + result);
                    System.out.println("value1: " + aList.get(i));
                    System.out.println("value2: " + aList.get(j));
                    System.out.println("value3: " + aList.get(k));
                }
                if(sum < FIND) {
                    j++;
                } else {
                    k--;
                }
            }
        }
    }
//    ===================
//    result: -3
//    value1: -4
//    value2: -1
//    value3: 2
//            ===================
//    result: -1
//    value1: -4
//    value2: 1
//    value3: 2
//            ===================
//    result: 2
//    value1: -1
//    value2: 1
//    value3: 2
}
