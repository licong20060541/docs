package com.algorithm.alist;

/**
 * 两个队列(排好序)的中间值
 * 时间复杂度O(log(m+n)) 空间复杂度(log(m+n))
 */
public class Algorithm005 {

    public static void main(String args[]) {
        findMedianSortedArrays();
    }

    private static void findMedianSortedArrays() {
        int aa[] = new int[] {2, 4, 6, 8, 10, 12};
        int bb[] = new int[] {1, 3, 5, 7, 9, 11}; // --- result = 6.5
//        int bb[] = new int[] {1, 3, 5, 7, 9, 11, 13};--- result = 7
        int m = aa.length;
        int n = bb.length;
        int total = m + n;
        double result;

        if(total % 2 == 1) {
            result = find(aa, 0, m, bb, 0, n, total / 2 + 1);
        } else {
            result = find(aa, 0, m, bb, 0, n, total / 2) + find(aa, 0, m, bb, 0, n, total / 2 + 1);
            result /= 2;
        }
        System.out.println("" + result);
    }

    private static double find(int[] aa, int aaStart, int m, int[] bb, int bbStart, int n, int k) {
        if(m > n) return find(bb, bbStart, n, aa, aaStart, m, k);
        if(m == 0) return bb[bbStart + k - 1];
        if(k == 1) return Math.min(aa[aaStart], bb[bbStart]);

        int ia = Math.min(k/2, m); // m 和 k 指的都是数量值，从1开始明显
        int ib = k - ia;

        if(aa[aaStart + ia - 1] < bb[bbStart+ib-1]) {
            // aa[aaStart + ia - 1]之前的都要抛弃，因此从aaStart+ia开始之后的有效
            return find(aa, aaStart+ia, m-ia, bb, bbStart, n, k-ia);
        } else if(aa[aaStart + ia] > bb[bbStart+ib]) {
            return find(aa, aaStart, m, bb, bbStart + ib, n-ib, k-ib);
        } else {
            return aa[aaStart + ia - 1];
        }

    }


}
