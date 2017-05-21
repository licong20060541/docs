package com.algorithm.gfind;

/**
 * Search Insert Position
 * 时间复杂度O(log n) 空间复杂度O(1)
 *
 * Given a sorted array and a target value, return the index if the target is found. If not,
 * return the index where it would be if it were inserted in order.
 You may assume no duplicates in the array.
 Here are few examples.
 [1,3,5,6], 5 → 2
 [1,3,5,6], 2 → 1
 [1,3,5,6], 7 → 4
 [1,3,5,6], 0 → 0

 */
public class Algorithm009 {

    /**
     * 二分法即可
     * @param A
     * @param target
     * @return
     */
    public int searchInsert(int[] A, int target) {

        if(A == null || A.length == 0) {
            return 0;
        }

        int l = 0;
        int r = A.length-1;

        while(l<=r) {
            int mid = (l+r)/2;
            if(A[mid]==target)
                return mid;
            if(A[mid]<target)
                l = mid+1;
            else
                r = mid-1;
        }

        return l;
    }

}
