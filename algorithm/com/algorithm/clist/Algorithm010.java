package com.algorithm.clist;

/**
 * Copy List with Random Pointer
 * A linked list is given such that each node contains an additional random pointer
 * which could point to any node in the list or null.
 * Return a deep copy of the list.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm010 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }


    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode random;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    ListNode copyRandomList(ListNode head) {
        for (ListNode cur = head; cur != null; ) {
            ListNode node = new ListNode(cur.val);
            node.next = cur.next;
            cur.next = node;
            cur = node.next;
        }
        for (ListNode cur = head; cur != null; ) {
            if (cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }

        // 分拆两个单链表
        ListNode dummy = new ListNode(-1);
        for (ListNode cur = head, new_cur = dummy; cur != null; ) {
            new_cur.next = cur.next;
            new_cur = new_cur.next;
            cur.next = cur.next.next;
            cur = cur.next;
        }
        return dummy.next;
    }

}
