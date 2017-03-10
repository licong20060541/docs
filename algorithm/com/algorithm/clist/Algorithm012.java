package com.algorithm.clist;

/**
 * Linked List Cycle II
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * Follow up: Can you solve it without using extra space?
 * 时间复杂度O(n) 空间复杂度O(1)
 *
 * 一般是前面几个如50个是单链表形式，之后n个如20个构成了环
 *
 * 为什么有环的情况下二者一定会相遇呢？因为fast先进入环，在slow进入之后，
 * 如果把slow看作在前面，fast在后面每次循环都向slow靠近1，所以一定会相遇，而不会出现fast直接跳过slow的情况。
 * 或者：情况1： slow位置 = fast + 1，那么再走一步就相遇
 *      情况2： slow位置 = fast + 2，那么再走一步就进入情况1
 */
public class Algorithm012 {

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
     * 当fast和slow相遇时，slow肯定没有遍历完链表，而fast已经在环内循环了n圈(n>=1)
     * 假设slow走了s步，则fast走了2s步(fast步数还等于s加上在换上多转的n圈)
     * 设环长为r，则：
     *   2s = s + nr
     *   s = nr
     * 设整个链表长L，起点到环入口点的距离为x，环入口点与相遇点距离为a，则
     *   x + a = nr = (n–1)r + r = (n − 1)r + L − x
     *   x = (n − 1)r + (L–x–a)
     * L–x–a为相遇点到环入口点的距离，由此可知，
     *   从链表头到环入口点等于 n-1 圈内环 + 相遇点到环入口点，
     * 于是我们可以从head开始另设一个指针slow2，两个慢指针每次前进一步，她们一定会在环入口点相遇
     */
    class Solution {

        ListNode detectCycle(ListNode head) {
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    ListNode slow2 = head;
                    while (slow2 != slow) {
                        slow2 = slow2.next;
                        slow = slow.next;
                    }
                    return slow2;
                }
            }
            return null;
        }
    }

}
