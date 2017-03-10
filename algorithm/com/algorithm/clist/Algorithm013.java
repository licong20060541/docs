package com.algorithm.clist;

/**
 * Reorder List
 * Given a singly linked list L : L 0 → L 1 → · · · → L n−1 → L n ,
 * reorder it to: L 0 → L n → L 1 → L n−1 → L 2 → L n−2 → · · ·
 * You must do this in-place without altering the nodes’ values.
 * For example, Given {1,2,3,4}, reorder it to {1,4,2,3}.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm013 {

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

    /**
     * 找到中间节点，断开，把后半截单链表reverse，再合并两个单链表
     */
    class Solution {
        void reorderList(ListNode head) {
            if (head == null || head.next == null) return;
            ListNode slow = head, fast = head, prev = null;
            while (fast != null && fast.next != null) {
                prev = slow;
                slow = slow.next;
                fast = fast.next.next;
            }
            prev.next = null; // cut at middle

            slow = reverse(slow);

            // merge two lists
            ListNode curr = head;
            while (curr.next != null) {
                ListNode tmp = curr.next;
                curr.next = slow;
                slow = slow.next;
                curr.next.next = tmp;
                curr = tmp;
            }
            curr.next = slow;
        }

        /**
         * 翻转单链表
         * @param head
         * @return
         */
        ListNode reverse(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode prev = head;
            for (ListNode curr = head.next, next = curr.next; curr != null;
                 prev = curr, curr = next, next = next != null ? next.next : null) {
                curr.next = prev;
            }
            head.next = null;
            return prev;
        }
    }

    ;

}
