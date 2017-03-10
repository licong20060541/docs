package com.algorithm.clist;

/**
 * Linked List Cycle
 * Given a linked list, determine if it has a cycle in it.
 * Follow up: Can you solve it without using extra space?
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm011 {

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

    class Solution {

        boolean hasCycle(ListNode head) {
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) return true;
            }
            return false;
        }
    }

}
