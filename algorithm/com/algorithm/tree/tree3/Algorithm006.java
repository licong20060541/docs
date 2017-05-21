package com.algorithm.tree.tree3;

/**
 * Populating Next Right Pointers in Each Node
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 *
 * Given a binary tree
 struct TreeLinkNode {
 int val;
 TreeLinkNode *left, *right, *next;
 TreeLinkNode(int x) : val(x), left(NULL), right(NULL), next(NULL) {}
 };
 Populate each next pointer to point to its next right node. If there is no next right node,
 the next pointer should be set to NULL.
 Initially, all next pointers are set to NULL. Note:
 • Youmayonlyuseconstantextraspace.
 • You may assume that it is a perfect binary tree (ie, all leaves are at the same level,
 and every parent
 has two children).
 For example, Given the following perfect binary tree,
 1
 /\
 23
 /\ /\
 4567
 After calling your function, the tree should look like:
 1 . NULL
 /\
 2 . 3 . NULL
 /\ /\
 4.5.6.7 . NULL

 参考1.12题目

 */
public class Algorithm006 {

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

    class Solution {
        
        void connect(TreeNode root) {
            connect(root, null);
        } 
        
        void connect(TreeNode root, TreeNode sibling) {
            if (root == null)
                return; else
                root.next = sibling;
            connect(root.left, root.right);// 左指向右
            if (sibling != null)
                connect(root.right, sibling.left); //右指向下个左
            else
                connect(root.right, null);
        } };
    
}
