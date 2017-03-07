package com.algorithm.clist;

/**
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 * For example: Given 1->2->3->4->5->nullptr and k = 2, return 4->5->1->2->3->nullptr.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm006 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }

        rotateRight(head.next, 2);

    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    static void rotateRight(ListNode head, int k) {
        if (head == null || k == 0) return;
        int len = 1;
        ListNode p = head;
        while (p.next != null) { // 长度
            len++;
            p = p.next;
        }
        k = len - k % len;
        p.next = head; // 首尾连接
        for (int step = 0; step < k; step++) {
            p = p.next;
        }
        head = p.next; // 新的首节点
        p.next = null; // 断开

        ListNode tmp = head;
        while (tmp != null) {
            System.out.println(tmp.val);
            tmp = tmp.next;
        }
    }

}
