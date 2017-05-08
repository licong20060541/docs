package com.algorithm.tree.tree2;

/**
 * Convert Sorted List to Binary Search Tree
 * <p>
 * Given a singly linked list where elements are sorted in ascending order,
 * convert it to a height balanced BST.
 * 单链表不能随机访问，自顶向下的二分法必须需要RandomAccessIt- erator
 * 存在一种自底向上的方法(bottom-up)
 * http://leetcode.com/2010/11/convert-sorted-list-to-balanced- binary.html
 */
public class Algorithm007 {

    public static void main(String args[]) {

    }

    private class MyList {
        Integer val;
        MyList next;
    }

    private class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer x) {
            val = x;
        }
    }

    /**
     * 分治法，自顶向下
     * 时间复杂度O(n^2) 空间复杂度O(n log n)
     */
    class Solution {
        TreeNode sortedListToBST(MyList head) {
            return sortedListToBST(head, listLength(head));
        }

        TreeNode sortedListToBST(MyList head, int len) {
            if (len == 0) return null;
            if (len == 1) return new TreeNode(head.val);
            TreeNode root = new TreeNode(nth_node(head, len / 2 + 1).val);
            root.left = sortedListToBST(head, len / 2);
            root.right = sortedListToBST(nth_node(head, len / 2 + 2),
                    (len - 1) / 2);
            return root;
        }

        int listLength(MyList node) {
            int n = 1;
            while (node.next != null) {
                n++;
                node = node.next;
            }
            return n;
        }

        /**
         * @param node
         * @param n:从0开始计算
         * @return
         */
        MyList nth_node(MyList node, int n) {
            while (n-- > 0)
                node = node.next;
            return node;
        }
    }


    //  ============================================  //

    /**
     * 自底向上
     * 时间复杂度O(n) 空间复杂度O(log n)
     */
    class Solution2 {
        private class ListNode {
            Integer val;
            ListNode next;
        }

        TreeNode sortedListToBST(ListNode head) {
            int len = 0;
            ListNode p = head;
            while (p != null) {
                len++;
                p = p.next;
            }
            return sortedListToBST(head, 0, len - 1);
        }

        TreeNode sortedListToBST(ListNode list, int start, int end) {
            if (start > end) return null;
            int mid = start + (end - start) / 2;
            TreeNode leftChild = sortedListToBST(list, start, mid - 1);
            TreeNode parent = new TreeNode(list.val);
            parent.left = leftChild;
            list = list.next;
            parent.right = sortedListToBST(list, mid + 1, end);
            return parent;
        }
    }


}
