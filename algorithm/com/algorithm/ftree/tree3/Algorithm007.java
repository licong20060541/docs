package com.algorithm.ftree.tree3;

/**
 * Sum Root to Leaf Numbers
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path
 * could represent a number. An example is the root-to-leaf path 1.2.3
 * which represents the number 123.
 Find the total sum of all root-to-leaf numbers.
 For example,
 1 
 /\ 
 23
 The root-to-leaf path 1.2 represents the number 12. The root-to-leaf
 path 1.3 represents the number 13.
 Return the sum =12 + 13 = 25.
 *
 */
public class Algorithm007 {

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

        int sumNumbers(TreeNode root) {
            return dfs(root, 0);
        }

        int dfs(TreeNode root, int sum) {
            if (root == null) return 0;
            if (root.left == null && root.right == null)
                return sum * 10 + root.val;
            return dfs(root.left, sum * 10 + root.val)
                    + dfs(root.right, sum * 10 + root.val);
        }
    }


}
