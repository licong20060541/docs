package com.algorithm.ftree.tree3;

/**
 * Maximum Depth of Binary Tree
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * Given a binary tree, find its maximum depth.
 The maximum depth is the number of nodes along the longest path
 from the root node down to the farthest leaf node.
 */
public class Algorithm002 {

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

//    class Solution {
//        int maxDepth(TreeNode *root) {
//            if (root == nullptr) return 0;
//            return max(maxDepth(root->left), maxDepth(root->right)) + 1;
//        }
//    }
    
}
