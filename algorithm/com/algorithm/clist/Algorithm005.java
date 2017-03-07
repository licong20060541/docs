package com.algorithm.clist;

/**
 * Given a sorted linked list, delete all nodes that have duplicate numbers,
 * leaving only distinct numbers from the original list.
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm005 {

    public static void main(String args[]) {

//        int[] datas = new int[]{1, 2, 3, 3, 4, 4, 6};
        int[] datas = new int[]{1, 1, 1, 2, 3};
        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 0; i < datas.length; i++) {
            tmp.next = new ListNode(datas[i]);
            tmp = tmp.next;
        }
//        deleteDuplicates(head);//推荐

        tmp = deleteDuplicates2(head.next);
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

    /**
     * 时间复杂度O(n) 空间复杂度O(1)
     */
    static void deleteDuplicates(ListNode head) {
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            while (cur.next != null && cur.val == cur.next.val) {
                cur = cur.next;
            }
            if (pre.next == cur) {
                pre = pre.next;
            } else {
                pre.next = cur.next;
            }
            cur = cur.next;
        }

        ListNode tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.next.val);
            tmp = tmp.next;
        }
    }


    /**
     * 递归
     */
    static ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = head.next;
        if (head.val == p.val) {
            // 过滤重复值，找到不相同的，同时还会舍弃head
            while (p != null && head.val == p.val) {
                p = p.next;
            }
            return deleteDuplicates2(p);
        } else {
            // 表示找到一个有效值
            head.next = deleteDuplicates2(head.next);
            return head;
        }
    }

}
