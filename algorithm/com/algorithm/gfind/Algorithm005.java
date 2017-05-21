package com.algorithm.gfind;

/**
 * Sort List
 * 空间O(1)
 * Sort a linked list in O(nlogn) time using constant space complexity.
 * 常数空间且O(nlogn)，单链表适合用归并排序，双链表适合用快速排序，复用2题
 */
public class Algorithm005 {
    
    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    ListNode sortList(ListNode head) {
        if (head ==  null || head.next ==  null)return head;
        // 快慢指针找到中间节点
        ListNode fast = head, slow = head;
        while (fast.next !=  null && fast.next.next !=  null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 断开
        fast = slow;
        slow = slow.next;
        fast.next =  null;
        // 前半段排序
        ListNode l1 = sortList(head);
        // 后半段排序，特殊情况如即前后各只有一个元素
        ListNode l2 = sortList(slow);
        return mergeTwoLists(l1, l2);
    }

    // Merge Two Sorted Lists
    ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        for (ListNode p = dummy; l1 != null || l2 != null; p = p.next) {
            int val1 = l1 == null? Integer.MAX_VALUE : l1.val;
            int val2 = l2 == null ? Integer.MAX_VALUE : l2.val;
            if (val1 <= val2) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
        }
        return dummy.next;
    }


}
