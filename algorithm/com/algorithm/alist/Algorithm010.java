package com.algorithm.alist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 四数字之和
 * 时间复杂度O(n^3) 空间复杂度O(1)
 */
public class Algorithm010 {

    public static void main(String args[]) {
        method1();
//        method2();
//        method3();
//        method4();
    }

    private static void method1() {
        Integer[] nums = new Integer[]{1, 0, -1, 0, -2, 2};

        int FIND = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);
        // 2, 夹逼
        for(int i = 0; i < aList.size() - 3; i++) {
            for( int j = i + 1; j < aList.size() - 2; j++) {
                int m = j + 1;
                int n = aList.size() - 1;
                while (m < n) {
                    int sum = aList.get(i) + aList.get(j) + aList.get(m) + aList.get(n);
                    if(sum < FIND) {
                        m++;
                    } else if(sum > FIND) {
                        n--;
                    } else {
                        System.out.println("===================");
                        System.out.println("value1: " + aList.get(i));
                        System.out.println("value2: " + aList.get(j));
                        System.out.println("value3: " + aList.get(m));
                        System.out.println("value4: " + aList.get(n));
                        m++;
                        n--;
                    }
                }
            }
        }
    }

//    ===================
//    value1: -2
//    value2: -1
//    value3: 1
//    value4: 2
//            ===================
//    value1: -2
//    value2: 0
//    value3: 0
//    value4: 2
//            ===================
//    value1: -1
//    value2: 0
//    value3: 0
//    value4: 1

    /**
     * use map
     */
    private static void method2() {
        Integer[] nums = new Integer[]{1, 0, -1, 0, -2, 2};

        int FIND = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);





    }

    private static void method3() {

    }

    private static void method4() {

    }

}
