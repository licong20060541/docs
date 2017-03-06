package com.algorithm.clist;

/**
 * Reverse a linked list from position m to n.
 * Do it in-place and in one-pass.
 * For example: Given 1->2->3->4->5->nullptr, m = 2 and n = 4,
 * return 1->4->3->2->5->nullptr.
 * Note: Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.
 * 时间复杂度O(m+n) 空间复杂度O(1)
 */
public class Algorithm002 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }
//        new Solution().reverseBetween(head, 1, 2);
        new Solution().reverseBetween(head, 2, 4);
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    private static class Solution {

        /**
         * @param head : 指向头的指针，不属于实际数据
         * @param m
         * @param n
         */
        public void reverseBetween(ListNode head, int m, int n) {
            // be sure
            if (m >= n) return;
            if (m <= 0) return;

            ListNode tmp;

            // prev代表了首元素并且是不变的，如上述的值2，head2为上述的值1位置
            //  1->2->3->4->5->nullptr, m = 2 and n = 4
            ListNode start = head;
            ListNode prevStart = head;
            for (int i = 0; i < m; i++) {
                prevStart = start;
                start = start.next;
            }
            ListNode cur = start.next;
            for (int i = m; i < n; ++i) {
                start.next = cur.next; // 2的下一个元素
                cur.next = prevStart.next;
                prevStart.next = cur;
                cur = start.next;
            }

            // print
            System.out.println();
            tmp = head;
            while (tmp.next != null) {
                System.out.println(tmp.next.val);
                tmp = tmp.next;
            }

        }
    }

}
