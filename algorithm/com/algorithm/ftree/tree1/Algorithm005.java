package com.algorithm.ftree.tree1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Binary Tree Level Order Traversal II
 * 时间复杂度O(n) 空间复杂度O(n)
 * Given a binary tree, return the bottom-up level order traversal of its nodes’ values.
 * (ie, from left to right, level by level from leaf to root).
 For example: Given binary tree {3,9,20,#,#,15,7},
 3
 / \
 9 20
 / \
 15
 7
 return its bottom-up level order traversal as:
 [
 [15,7]
 [9,20],
 [3],
 ]
 */
public class Algorithm005 {

    public static void main(String args[]) {

    }

    public class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer x) {
            val = x;
        }
    }


    /**
     * 递归版本
     */
    class Solution {
        Vector<Vector<Integer> > levelOrderBottom(TreeNode root) {
            Vector<Vector<Integer>> result = new Vector<>();
            traverse(root, 1, result);
            reverse(result); // 比上一题多此行
            return result;
        }
        void traverse(TreeNode root, int level, Vector<Vector<Integer>> result) {
            if (root == null) return;
            if (level > result.size())
                result.add(new Vector<Integer>());
            result.get(level-1).add(root.val);
            traverse(root.left, level+1, result);
            traverse(root.right, level+1, result);
        }
    }


    /**
     * 迭代
     */
    class Solution2 {
        Vector<Vector<Integer> > levelOrder(TreeNode root) {
            Vector<Vector<Integer> > result = new Vector<>();
            Queue<TreeNode> current = new LinkedList<>();
            Queue<TreeNode> next = new LinkedList<>();
            Queue<TreeNode> tmpList;

            if(root == null) {
                return result;
            } else {
                current.add(root);
            }
            while (current.size() != 0) {
                Vector<Integer> level = new Vector<>(); // elments in one level
                while (current.size() != 0) {
                    TreeNode node = current.poll();
                    level.add(node.val);
                    if (node.left != null) next.offer(node.left);
                    if (node.right != null) next.offer(node.right);
                }
                result.add(level);
                // swap
                tmpList = next;
                next = current;
                current = tmpList;
            }
            reverse(result); //比上一题多此行
            return result;
        }
    }

    public static void reverse(Vector<Vector<Integer> > result) {
        // TODO: 17-4-19
    }


}
