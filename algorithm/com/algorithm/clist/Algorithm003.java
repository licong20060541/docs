package com.algorithm.clist;

/**
 * Partition List
 * Given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.
 * 时间复杂度O(m+n) 空间复杂度O(1)
 *
 * 给定一个单链表和一个x，把链表中小于x的放到前面，大于等于x的放到后面，每部分元素的原始相对位置不变。
 */
public class Algorithm003 {

    public static void main(String args[]) {

        int[] datas = new int[] {1, 4, 3, 2, 5, 2};
        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 0; i < datas.length; i++) {
            tmp.next = new ListNode(datas[i]);
            tmp = tmp.next;
        }

        new Solution().partition(head, 3);

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
         * head : 指向头的指针，不属于实际数据
         */
        public void partition(ListNode head, int x) {
            ListNode smallerHead = new ListNode(-1);
            ListNode biggerHead = new ListNode(-1);
            ListNode smaller = smallerHead, bigger = biggerHead;

            for (ListNode cur = head; cur != null; cur = cur.next) {
                if (cur.val < x) {
                    smaller.next = cur;
                    smaller = cur;
                } else {
                    bigger.next = cur;
                    bigger = cur;
                }
            }
            smaller.next = biggerHead.next;
            bigger.next = null;

            ListNode tmp = head;
            while (tmp.next != null) {
                System.out.println(tmp.next.val);
                tmp = tmp.next;
            }
        }

    }

}
