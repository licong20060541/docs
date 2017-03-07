package com.algorithm.clist;

/**
 * Swap Nodes in Pairs
 * For example, Given 1->2->3->4, you should return the list as 2->1->4->3.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm008 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }

        swapPairs(head);

    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    static void swapPairs(ListNode head) {
        for (ListNode prev = head, cur = prev.next, next = cur.next;
             next != null;
             prev = cur, cur = cur.next, next = cur != null ? cur.next : null) {
            prev.next = next;
            cur.next = next.next;
            next.next = cur;
        }

        ListNode tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.next.val);
            tmp = tmp.next;
        }
    }


}
