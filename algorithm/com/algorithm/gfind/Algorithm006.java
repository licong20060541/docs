package com.algorithm.gfind;

/**
 * First Missing Positive  正数
 * O(n)  O(1)
 * Given an unsorted integer array, find the first missing positive integer.
 * For example, Given [1,2,0] return 3, and [3,4,-1,1] return 2.
 * Your algorithm should run in O(n) time and uses constant space.
 *
 * 本质上是桶排序，每当A[i] != i+1，将A[i]与A[A[i-1]]交换，直到无法交换为止，终止条件是A[i]=A[A[i-1]]
 */
public class Algorithm006 {
    
    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

//    class Solution {
//        int firstMissingPositive(vector<int>& nums) {
//            bucket_sort(nums);
//            for (int i = 0; i < nums.size(); ++i)
//                if (nums[i] != (i + 1))
//                    return i + 1;
//            return nums.size() + 1;
//        }
          // 假设：  4  2  1  3--3 1 2 4--2 1 3 4 - 2 1 3 4
//        static void bucket_sort(vector<int>& A) {
//            const int n = A.size();
//            for (int i = 0; i < n; i++) {
//                while (A[i] != i + 1) {
//                    if (A[i] <= 0 || A[i] > n || A[i] == A[A[i] - 1])
//                        break;
//                    swap(A[i], A[A[i] - 1]);
//                } }
//        } };


}
