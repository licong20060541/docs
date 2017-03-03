package com.algorithm.blist;

/**
 * 多个小朋友站成一排，根据他们的得分分发糖果，得分高的小朋友要比旁边得分低的小朋友得到的糖果多，
 * 每个小朋友至少得到一枚糖果，问最少要准备多少糖果？
 * 时间复杂度O(n) 空间复杂度O(n)
 */
public class Algorithm009 {

    public static void main(String args[]) {

        // 法1：迭代
//        先从左到右扫描一遍，使得右边比左边得分高的小朋友糖果数比左边多。
//        再从右到左扫描一遍，使得左边比右边得分高的小朋友糖果数比右边多。

        int ratings[] = new int[]{1, 3, 4, 5, 6, 2, 6, 4, 2};
        int n = ratings.length;
        int[] increment = new int[n];

        for (int i = 1, inc = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1])
                increment[i] = Math.max(inc++, increment[i]);
            else
                inc = 1;
        }
        for (int i = n - 2, inc = 1; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1])
                increment[i] = Math.max(inc++, increment[i]);
            else
                inc = 1;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += increment[i];
        }
        sum += n;
        System.out.print("==" + sum);


        // 法2：递归
        // TODO: 17-3-3

    }


}
