package com.algorithm.clist;

/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * For example,
 * Given 1->1->2, return 1->2.
 * Given 1->1->2->3->3, return 1->2->3.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm004 {

    public static void main(String args[]) {

        int[] datas = new int[]{1, 2, 2, 2, 5, 5, 6};
        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 0; i < datas.length; i++) {
            tmp.next = new ListNode(datas[i]);
            tmp = tmp.next;
        }
//        method1(head);
        deleteDuplicates(head);

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
     * 迭代版
     * 时间复杂度O(n) 空间复杂度O(1)
     */
    private static void method1(ListNode head) {
        for (ListNode pre = head.next, cur = pre.next; cur != null; cur = pre.next) {
            if (pre.val == cur.val) {
                pre.next = cur.next;
                cur.next = null;
            } else {
                pre = cur;
            }
        }

        ListNode tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.next.val);
            tmp = tmp.next;
        }
    }

    /**
     * 递归版
     * 时间复杂度O(n) 空间复杂度O(1)
     */
    static void deleteDuplicates(ListNode head) {
        if (head.next != null) {
            recur(head.next, head.next.next);
        }
        ListNode tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.next.val);
            tmp = tmp.next;
        }
    }

    static void recur(ListNode prev, ListNode cur) {
        if (cur == null) return;
        if (prev.val == cur.val) { //
            prev.next = cur.next;
            recur(prev, prev.next);
        } else {
            recur(prev.next, cur.next);
        }
    }

}
