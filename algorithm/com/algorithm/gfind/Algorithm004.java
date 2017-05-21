package com.algorithm.gfind;

/**
 * Insertion Sort List
 * 时间复杂度O(n^2) 空间复杂度O(1)
 * Sort a linked list using insertion sort.
 * 用插入排序对一个链表进行排序
 */
public class Algorithm004 {


    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public class Solution {
        ListNode searchForInsertPos(ListNode head, int val) {
            ListNode pre  = head;
            ListNode next = head.next;
            // 从头往后比较，类似和数组插入从后比
            while(next!=null&&next.val<=val) {
                pre  = next;
                next = next.next;
            }
            return pre;
        }

        public ListNode insertionSortList(ListNode head) {
            if(head==null||head.next==null) {
                return head;
            }

            ListNode dummy = new ListNode(-1);
            dummy.next = null;
            ListNode new_next;
            ListNode pre, next;
            while(head!=null) {
                // dummy是存储结果的，每次判断一个进入dummy队列一个
                // dummy的指向不变化
                next = head.next; // 依次判断下个元素，插入即是
                pre  = searchForInsertPos(dummy, head.val);
                new_next = pre.next;
                head.next = new_next;
                pre.next  = head;
                head = next;
            }
            return dummy.next;
        }
    }



}
