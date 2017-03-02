package com.algorithm.alist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 求三数和
 * 时间复杂度O(n^2) 空间复杂度O(1)
 */
public class Algorithm008 {

    public static void main(String args[]) {

        Integer[] nums = new Integer[]{-1, 0, 1, 2, -1, -4};

        int SUM = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);

        // 2, 夹逼
        for (int i = 0; i < aList.size() - 2; i++) {
            if (i > 0 && aList.get(i).equals(aList.get(i - 1))) {
                continue;
            }
            int j = i + 1;
            int k = aList.size() - 1;
            while (j < k) {
                if (aList.get(i) + aList.get(j) + aList.get(k) < SUM) {
                    j++;
                    while (aList.get(j).equals(aList.get(j - 1)) && j < k) j++;
                } else if (aList.get(i) + aList.get(j) + aList.get(k) > SUM) {
                    k--;
                    while (aList.get(k).equals(aList.get(k + 1)) && j < k) k--;
                } else {
                    System.out.println("--->" + aList.get(i)
                            + "--->" + aList.get(j) + "--->" + aList.get(k));
                    j++;
                    k--;
                    while (aList.get(j).equals(aList.get(j - 1))
                            && aList.get(k).equals(aList.get(k + 1))
                            && j < k) {
                        j++;
                    }
                }
            }
        }
    }

//    --->-1--->-1--->2
//    --->-1--->0--->1

}
