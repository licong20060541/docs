package com.algorithm.ftree.tree2;

/**
 * Validate Binary Search Tree
 * 时间复杂度O(n) 空间复杂度O(log n)
 Given a binary tree, determine if it is a valid binary search tree (BST).
 Assume a BST is defined as follows:
 • The left subtree of a node contains only nodes with keys less than the node’s key.
 • The right subtree of a node contains only nodes with keys greater than the node’s key.
 • Both the left and right subtrees must also be binary search trees.
 */
public class Algorithm005 {

    public static void main(String args[]) {
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    boolean isValidBST(TreeNode root) {
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    boolean isValidBST(TreeNode root, int lower, int upper) {
        if (root == null) return true;
        return root.val > lower && root.val < upper
                && isValidBST(root.left, lower, root.val)
                && isValidBST(root.right, root.val, upper);
    }

    
}
