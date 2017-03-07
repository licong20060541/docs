package com.algorithm.clist;

/**
 * Remove Nth Node From End of List
 * For example, Given linked list: 1->2->3->4->5, and n = 2
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm007 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }

        removeNthFromEnd(head, 2);

    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * p和q两个指针，让q先走n步，之后一起走，当q到尾节点时，删除p.next
     *
     * @param head
     * @param n
     */
    static void removeNthFromEnd(ListNode head, int n) {
        ListNode p = head, q = head;
        for (int i = 0; i < n; i++)
            q = q.next;
        while (q.next != null) {
            p = p.next;
            q = q.next;
        }
        p.next = p.next.next;

        ListNode tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.next.val);
            tmp = tmp.next;
        }

    }

}
