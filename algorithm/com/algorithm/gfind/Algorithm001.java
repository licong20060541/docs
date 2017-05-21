package com.algorithm.gfind;

/**
 * Merge Sorted Array
 * 时间复杂度O(m+n) 空间复杂度O(1)
 *
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 Note: You may assume that A has enough space to hold additional elements from B.
 The number of elements initialized in A and B are m and n respectively.
 */
public class Algorithm001 {

    class Solution {

        void merge(int[] A, int m, int[] B, int n) {
            int ia = m - 1, ib = n - 1, icur = m + n - 1;
            while(ia >= 0 && ib >= 0) {
                A[icur--] = A[ia] >= B[ib] ? A[ia--] : B[ib--];
            }
            while(ib >= 0) {
                A[icur--] = B[ib--];
            }
        }
    }


}
