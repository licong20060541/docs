package com.algorithm.alist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 四数字之和
 *
 */
public class Algorithm010 {

    public static void main(String args[]) {
//        method1();
//        method2();
        method3();
//        method4();
    }

    // 时间复杂度O(n^3) 空间复杂度O(1)
    private static void method1() {
        Integer[] nums = new Integer[]{1, 0, -1, 0, -2, 2};

        int FIND = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);
        // 2, 夹逼
        for (int i = 0; i < aList.size() - 3; i++) {
            for (int j = i + 1; j < aList.size() - 2; j++) {
                int m = j + 1;
                int n = aList.size() - 1;
                while (m < n) {
                    int sum = aList.get(i) + aList.get(j) + aList.get(m) + aList.get(n);
                    if (sum < FIND) {
                        m++;
                    } else if (sum > FIND) {
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


    //    ====================================================================================

    /**
     * use map
     * 时间复杂度平均O(n^2)最坏O(n^4) 空间复杂度O(n^2)
     */
    private static void method2() {
        Integer[] nums = new Integer[]{1, 0, -1, 0, -2, 2};

        int FIND = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);

        // -2, -1, 0, 0, 1, 2

        HashMap<Integer, List<Pair>> map = new HashMap<>();
        List<Result> results = new ArrayList<>();

        for (int i = 0; i < aList.size() - 1; i++) {
            for (int j = i + 1; j < aList.size(); j++) {
                int sum = aList.get(i) + aList.get(j);
                List<Pair> pairList = map.get(sum);
                if (pairList == null) {
                    pairList = new ArrayList<>();
                    map.put(sum, pairList);
                }
                pairList.add(new Pair(i, j));
            }
        }

        for (int i = 0; i < aList.size() - 1; i++) {
            for (int j = i + 1; j < aList.size(); j++) {
                int key = FIND - nums[i] - nums[j];
                if (map.containsKey(key)) {
                    List<Pair> pairList = map.get(key);
                    for (Pair pair : pairList) {
                        // 假设新获取的两个数位置大于之前存储的两个数位置，不然会重复
                        if (i <= pair.second) {
                           continue;
                        }
                        // 结果去重
                        boolean isExist = false;
                        for (Result result : results) {
                            if(result.a == aList.get(i)
                                    && result.b == aList.get(j)
                                    && result.c == aList.get(pair.first)
                                    && result.d == aList.get(pair.second)) {
                                isExist = true;
                                break;
                            }
                        }
                        if(!isExist) {
                            Result result = new Result(aList.get(i), aList.get(j),
                                    aList.get(pair.first), aList.get(pair.second));
                            results.add(result);
                            System.out.println("===================");
                            System.out.println("value3: " + aList.get(pair.first));
                            System.out.println("value4: " + aList.get(pair.second));
                            System.out.println("value1: " + aList.get(i));
                            System.out.println("value2: " + aList.get(j));
                        }
                    }
                }
            }
        }
    }

//    ===================
//    value3: -1
//    value4: 0
//    value1: 0
//    value2: 1
//            ===================
//    value3: -2
//    value4: 0
//    value1: 0
//    value2: 2
//            ===================
//    value3: -2
//    value4: -1
//    value1: 1
//    value2: 2

    static class Pair {
        // index
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static class Result {
        int a, b, c, d;

        public Result(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }



    //    ====================================================================================

    /**
     * list
     * 时间复杂度平均O(n^2) 空间复杂度O(n^2)
     */
    private static void method3() {
        Integer[] nums = new Integer[]{1, 0, -1, 0, -2, 2};

        int FIND = 0;

        // 1, 排序
        List<Integer> aList = Arrays.asList(nums);
        Collections.sort(aList);

        // -2, -1, 0, 0, 1, 2

        HashMap<Integer, List<Pair>> map = new HashMap<>();
        List<Item> allItems = new ArrayList<>();
        List<Result> results = new ArrayList<>();

        for (int i = 0; i < aList.size() - 1; i++) {
            for (int j = i + 1; j < aList.size(); j++) {
                int sum = aList.get(i) + aList.get(j);
                List<Pair> pairList = map.get(sum);
                if (pairList == null) {
                    pairList = new ArrayList<>();
                    map.put(sum, pairList);
                }
                pairList.add(new Pair(i, j));
                allItems.add(new Item(i, j, sum));
            }
        }

        // 遍历list
        for (Item item : allItems) {
            int x = FIND - item.sum;
            if(map.containsKey(x)) {
                List<Pair> pairList = map.get(x);
                for (Pair pair : pairList) {
                    int a = item.m;
                    int b = item.n;
                    int c = pair.first;
                    int d = pair.second;
                    if (b < c) {
                        // 结果去重
                        boolean isExist = false;
                        for (Result result : results) {
                            if(result.a == aList.get(a)
                                    && result.b == aList.get(b)
                                    && result.c == aList.get(c)
                                    && result.d == aList.get(d)) {
                                isExist = true;
                                break;
                            }
                        }
                        if(!isExist) {
                            Result result = new Result(aList.get(a), aList.get(b),
                                    aList.get(c), aList.get(d));
                            results.add(result);
                            System.out.println("===================");
                            System.out.println("value1: " + aList.get(a));
                            System.out.println("value2: " + aList.get(b));
                            System.out.println("value3: " + aList.get(c));
                            System.out.println("value4: " + aList.get(d));
                        }

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

    static class Item {
        int m, n, sum;

        public Item(int m, int n, int sum) {
            this.m = m;
            this.n = n;
            this.sum = sum;
        }
    }


    /**
     * 时间复杂度平均O(n^3logn) 空间复杂度O(1)
     * 比方法1慢
     */
    private static void method4() {
       // give up
    }

}
