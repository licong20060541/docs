package com.algorithm.tree.tree1;

/**
 * Populating Next Right Pointers in Each Node II
 * 时间复杂度O(n) 空间复杂度O(1)
 * Follow up for problem ”Populating Next Right Pointers in Each Node”.
 What if the given tree could be any binary tree? Would your previous solution still work?
 Note: You may only use constant extra space.
 For example, Given the following binary tree,
    1
  /   \
  2    3
 / \    \
 4  5    7

 After calling your function, the tree should look like:
    1 -> NULL
   /     \
  2 ->    3 -> NULL
 /   \      \
 4 -> 5->  7 -> NULL
 */
public class Algorithm012 {

    public static void main(String args[]) {
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 递归1
     */
    void connect(TreeNode root) {
        if (root == null) return;
        TreeNode dummy = new TreeNode(-1);
        for (TreeNode curr = root, prev = dummy; curr != null; curr = curr.next) {
            if (curr.left != null){
                prev.next = curr.left;
                prev = prev.next;
            }
            if (curr.right != null){
                prev.next = curr.right;
                prev = prev.next;
            }
        }
        // dummy的值是每行的第一个
        connect(dummy.next);
    }
    

    /**
     * 迭代
     */
    void connect2(TreeNode root) {
        while (root != null) {
            TreeNode next = null; // the first node of next level
            TreeNode prev = null; // previous node on the same level
            for (; root != null; root = root.next) {
                if (null == next) {
                    next = root.left != null ? root.left : root.right;
                }
                if (root.left != null) {
                    if (prev != null) prev.next = root.left;
                    prev = root.left;
                }
                if (root.right != null) {
                    if (prev != null) prev.next = root.right;
                    prev = root.right;
                }
            }
            root = next; // turn to next level
        }
    }

}
