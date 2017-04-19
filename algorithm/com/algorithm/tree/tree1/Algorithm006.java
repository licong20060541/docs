package com.algorithm.tree.tree1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Binary Tree Zigzag Level Order Traversal
 * 时间复杂度O(n) 空间复杂度O(n)
 Given a binary tree, return the zigzag level order traversal of its nodes’ values.
 (ie, from left to right,
 then right to left for the next level and alternate between).
 For example: Given binary tree 3,9,20,#,#,15,7,
 3
 / \
 9 20
 / \
 15
 7
 return its zigzag level order traversal as:
 [
 [3],
 [20,9],
 [15,7]
 ]
 */
public class Algorithm006 {

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

        Vector<Vector<Integer> > zigzagLevelOrder(TreeNode root) {
            Vector<Vector<Integer>> result = new Vector<>();
            traverse(root, 1, result, true);
            return result;
        }

        void traverse(TreeNode root, int level, Vector<Vector<Integer>> result,
                      boolean left_to_right) {
            if (root == null) return;
            if (level > result.size())
                result.add(new Vector<Integer>());
            if (left_to_right)
                result.get(level-1).add(root.val);
            else
                result.get(level-1).add(0, root.val);
            traverse(root.left, level+1, result, !left_to_right);
            traverse(root.right, level+1, result, !left_to_right);
        }
    }


    /**
     * 迭代
     */
    class Solution2 {
        Vector<Vector<Integer> > zigzagLevelOrder(TreeNode root) {
            Vector<Vector<Integer> > result = new Vector<>();
            Queue<TreeNode> current = new LinkedList<>();
            Queue<TreeNode> next = new LinkedList<>();
            Queue<TreeNode> tmpList;
            boolean left_to_right = true;
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
                    if (node.left != null) next.add(node.left);
                    if (node.right != null) next.add(node.right);
                }
                if (!left_to_right) reverse(level);
                result.add(level);
                left_to_right = !left_to_right;
                // swap
                tmpList = next;
                next = current;
                current = tmpList;
            }
            return result;
        }
    }

    public static void reverse(Vector<Integer> level) {
        // TODO: 17-4-19
    }


}
