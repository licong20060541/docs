package com.algorithm.blist;

/**
 * trapping rain water
 * For example, Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 * 柱子体间可存储的水容量
 */
public class Algorithm002 {

    public static void main(String args[]) {

//        method01();
        method02();

    }

    /**
     * 算法： 对于每个柱子，找到其左和右的最大值，该柱子容纳的水 = min(maxLeft, maxRight) - 当前柱子height
     */
    private static void method01() {
        int[] water = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] maxLeft = new int[water.length];
        int[] maxRight = new int[water.length];
        for (int i = 1; i < water.length; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], water[i - 1]);
            maxRight[water.length - 1 - i] = Math.max(
                    maxRight[water.length - i], water[water.length - i]);
        }
        int sum = 0;
        for (int i = 1; i < water.length; i++) {
            int height = Math.min(maxLeft[i], maxRight[i]);
            if (height > water[i]) {
                sum = sum + height - water[i];
            }
        }
        System.out.println(sum + "");

    }

    /**
     * 算法： 找到最高柱子，分左右两组处理
     */
    private static void method02() {
        int[] water = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int max = 0; // 序号
        for (int i = 1; i < water.length; i++) {
            if (water[max] < water[i]) {
                max = i;
            }
        }

        int sum = 0;
        for (int i = 0, peak = 0; i < max; i++)
            if (water[i] > peak) peak = water[i]; // 右边最大值已确定，找其左边
            else sum += peak - water[i]; // 右边最大值肯定大于左边最大值此时
        for (int i = water.length - 1, top = 0; i > max; i--)
            if (water[i] > top) top = water[i];
            else sum += top - water[i];
        System.out.println(sum + "");

    }

    /**
     * 利用栈？？
     */
    private static void method03() {
    }

}
