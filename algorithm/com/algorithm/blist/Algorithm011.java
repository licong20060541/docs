package com.algorithm.blist;

/**
 * Given an array of integers, every element appears three times except for one.
 * Find that single one.
 * Note: Your algorithm should have a linear runtime complexity.
 * Could you implement it without using extra memory?
 *
 */
public class Algorithm011 {

    public static void main(String args[]) {
//        method1();
        method2();
    }

    /**
     * 创建一个数组count[sizeof(int)], count[i]表示在第i位上出现1的次数
     * 如果 count[i] 是3的整数倍，则忽略，否则有效
     */
    private static void method1() {

        int[] nums = new int[]{2, 3, 5, 7, 2, 5, 3, 3, 2, 5};

        int W = Integer.SIZE;
        int[] count = new int[W]; // count[i]

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < W; j++) {
                count[j] += (nums[i] >> j) & 1;
                count[j] %= 3;
            }
        }
        int result = 0;
        for (int i = 0; i < W; i++) {
            result += (count[i] << i);
        }
        System.out.print("" + result);
    }

    /**
     * 用one记录到当前处理的元素为止，二进制1出现‘1次’(mod 3之后的1)的有哪些二进制位
     * 用two记录到当前处理的元素为止，二进制1出现‘2次’(mod 3之后的2)的有哪些二进制位
     * 当one和two中某一位同时为1时表示该二进制位上1出现了3次，此时需要清零，
     * 即二进制模拟三进制。one即为结果
     */
    private static void method2() {
        int[] nums = new int[]{2, 3, 5, 7, 2, 5, 3, 3, 2, 5};
        int one = 0, two = 0, three;
        for (int i : nums) {
            two |= (one & i);
            one ^= i;
            three = ~(one & two);
            one &= three;
            two &= three;
        }
        System.out.print("" + one);
    }


}
