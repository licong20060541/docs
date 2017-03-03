package com.algorithm.blist;

/**
 * 爬楼梯：一次可爬1步到2步，问到第n层有几种方法
 * 算法： f(n) = f(n-1) + f(n-2)
 *      从第n-1层爬一步或从n-2层爬2步
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm005 {

    public static void main(String args[]) {

        int N = 20;

        // 迭代
        int prev = 0; //地面
        int cur = 1; // 第一层
        for (int i = 1; i <= N; i++) {
            int tmp = cur;
            cur += prev;
            prev = tmp;
        }
        System.out.println("" + cur); //10946

        // 法2：斐波那契公式，略


    }

}
