package com.algorithm.ftree.tree1;

/**
 * Balanced Binary Tree
 * 时间复杂度O(n) 空间复杂度O(log n)
 * Given a binary tree, determine if it is height-balanced.
 For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two
 subtrees of every node never differ by more than 1.
 */
public class Algorithm010 {

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

    boolean isBalanced (TreeNode root) {
        return balancedHeight(root) >= 0;
    }

    /**
     * Returns the height of `root` if `root` is a balanced tree,
     * otherwise, returns `-1`.
     */
    private int balancedHeight (TreeNode root) {
        if (root == null) return 0; // 终止条件
        int left = balancedHeight (root.left);
        int right = balancedHeight (root.right);
        if (left < 0 || right < 0 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1; // 三方合并
    }

}
