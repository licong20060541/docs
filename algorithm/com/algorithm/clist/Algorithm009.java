package com.algorithm.clist;

/**
 * Reverse Nodes in k-Group !!! 连续翻转k个，翻转多组
 * Only constant memory is allowed.
 * For example, Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5 ！！！
 * For k = 3, you should return: 3->2->1->4->5
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm009 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }

        tmp = new Solution1().reverseKGroup(head.next, 2);
        while (tmp != null) {
            System.out.println(tmp.val);
            tmp = tmp.next;
        }

    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    static class Solution1 {

        /**
         * 连续翻转k个，翻转多组
         *
         * @param head
         * @param k
         * @return
         */
        static ListNode reverseKGroup(ListNode head, int k) {
            if (head == null || head.next == null || k < 2) return head;
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            for (ListNode prev = dummy, end = head; end != null; end = prev.next) {
                // 找到一组k个
                for (int i = 1; i < k && end != null; i++) {
                    end = end.next;
                }
                if (end == null) break;
                // 翻转
                prev = reverse(prev, prev.next, end);
            }
            return dummy.next;
        }

        /**
         * 翻转指定范围的链表
         *
         * @param prev  : first前一个元素
         * @param begin ：
         * @param end   : [begin, end] 闭区间，保证三者都不为null
         * @return ： 返回翻转后的倒数第一个元素
         */
        static ListNode reverse(ListNode prev, ListNode begin, ListNode end) {
            ListNode end_next = end.next;
            for (ListNode p = begin, cur = p.next, next = cur.next;
                 cur != end_next;
                 p = cur, cur = next, next = next != null ? next.next : null) {

                cur.next = p;
            }
            begin.next = end_next;
            prev.next = end;
            return begin;
        }

    }


    /**
     * Good!
     */
    static class Solution2 {

        static ListNode reverseKGroup(ListNode head, int k) {
            if (head == null || head.next == null || k < 2) {
                return head;
            }
            ListNode next_group = head;
            for (int i = 0; i < k; ++i) {
                if (next_group != null)
                    next_group = next_group.next;
                else
                    return head;
            }

            // next_group is the head of next group
            // new_next_group is the new head of next group after reversion
            ListNode new_next_group = reverseKGroup(next_group, k);
            ListNode prev = null, cur = head;
            while (cur != next_group) {
                ListNode next = cur.next;
                cur.next = prev != null ? prev : new_next_group;
                prev = cur;
                cur = next;
            }
            return prev; // prev will be the new head of this group
        }
    }

}
