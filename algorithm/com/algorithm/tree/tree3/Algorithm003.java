package com.algorithm.tree.tree3;

/**
 * Path Sum
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf
 * path such that adding up all the values along the path equals the given sum.
 For example: Given the below binary tree and sum = 22,

 5
 / \
 4  8
 /   /\
 11 13 4
 /\    \
 7 2   1
 return true, as there exist a root-to-leaf path 5.4.11.2 which sum is 22.

 */
public class Algorithm003 {

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

    boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) // leaf
            return sum == root.val;
        return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
    }
    
}
