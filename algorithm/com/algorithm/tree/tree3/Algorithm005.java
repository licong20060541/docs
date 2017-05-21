package com.algorithm.tree.tree3;

/**
 * Binary Tree Maximum Path Sum
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * Given a binary tree, find the maximum path sum.
 The path may start and end at any node in the tree. For example: Given the below binary tree,
 1
 /\
 23
 Return 6.

 从任意节点开始，任意节点结束

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

    class Solution {
        
        int maxPathSum(TreeNode root) {
            max_sum = Integer.MIN_VALUE;
            dfs(root);
            return max_sum;
        } 
        
        int max_sum;
        // 带入简单例子思考
        int dfs( TreeNode root) {
            if (root == null) return 0;
            int l = dfs(root.left);
            int r = dfs(root.right);
            int sum = root.val;
            if (l > 0) sum += l;
            if (r > 0) sum += r;
            max_sum = Math.max(max_sum, sum);
            return Math.max(r, l) > 0 ? Math.max(r, l) + root.val : root.val;
        }
    }
    
}
