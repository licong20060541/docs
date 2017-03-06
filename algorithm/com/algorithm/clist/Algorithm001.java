package com.algorithm.clist;

import java.util.ArrayList;

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * <p>
 * 时间复杂度O(m+n) 空间复杂度O(1)
 */
public class Algorithm001 {

    public static void main(String args[]) {

        ArrayList<Integer> aList = new ArrayList<>();
        ArrayList<Integer> bList = new ArrayList<>();
        ArrayList<Integer> sumList = new ArrayList<>();

        aList.add(2);
        aList.add(4);
        aList.add(3);

        bList.add(5);
        bList.add(6);
        bList.add(4);

        int sum = 0;

        for (int i = 0; i < aList.size() && i < bList.size(); i++) {
            sum += aList.get(i) + bList.get(i);
            sumList.add(sum % 10);
            sum = sum / 10;
        }
        if (sum > 0) {
            sumList.add(sum);
        }

        for (int i = 0; i < sumList.size(); i++) {
            System.out.println("==" + sumList.get(i));
        }

    }
//            ==7
//            ==0
//            ==8

}
