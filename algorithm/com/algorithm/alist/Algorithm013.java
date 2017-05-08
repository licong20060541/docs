package com.algorithm.alist;

/**
 * [1,2,3,...,n] 找出第k大的组合数，
 * eg: [1, 2, 3]找第2大，则 132
 */
public class Algorithm013 {

    public static void main(String args[]) {

        // 法1： 调用k-1次上题方法

        // 法2： 康托编码 自行百度其算法吧，时间复杂度O(n),空间(1)

    }


//
//    在n!个排列中，第一位的元素总是(n-1)!一组出现的，也就说如果p = k / (n-1)!，
//    那么排列的最开始一个元素一定是nums[p]。
//
//    假设有n个元素，第K个permutation是
//    a1, a2, a3, .....   ..., an
//    那么a1是哪一个数字呢？
//    那么这里，我们把a1去掉，那么剩下的permutation为
//    a2, a3, .... .... an, 共计n-1个元素。 n-1个元素共有(n-1)!组排列，那么这里就可以知道
//            设变量K1 = K
//    a1 = K1 / (n-1)!
//    同理，a2的值可以推导为
//            a2 = K2 / (n-2)!
//    K2 = K1 % (n-1)!
//            .......
//    a(n-1) = K(n-1) / 1!
//    K(n-1) = K(n-2) /2!
//    an = K(n-1)
//            [cpp] view plain copy print?
//    string getPermutation(int n, int k) {
//        vector<int> nums(n);
//        int pCount = 1;
//        for(int i = 0 ; i < n; ++i) {
//            nums[i] = i + 1;
//            pCount *= (i + 1);
//        }
//
//        k--;
//        string res = "";
//        for(int i = 0 ; i < n; i++) {
//            pCount = pCount/(n-i);
//            int selected = k / pCount;
//            res += ('0' + nums[selected]);
//
//            for(int j = selected; j < n-i-1; j++)
//                nums[j] = nums[j+1];
//            k = k % pCount;
//        }
//        return res;
//    }


}
