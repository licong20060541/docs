package com.algorithm.tree.tree1;

import java.util.Stack;

/**
 * Same Tree
 * Given two binary trees, write a function to check if they are equal or not.
 Two binary trees are considered equal if they are structurally identical
 and the nodes have the same value.
 * 时间复杂度O(n) 空间复杂度O(log n)
 */
public class Algorithm008 {

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

    /**
     * 递归
     *
     * @return
     */
    boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val
                && isSameTree(p.left, q.left)
                && isSameTree(p.right, q.right);
    }


    /**
     * 迭代
     * @return
     */
    boolean isSameTree2(TreeNode p, TreeNode q) {
        Stack<TreeNode> s = new Stack<>();
        s.push(p);
        s.push(q);
        while(!s.empty()) {
            p = s.peek();
            s.pop();
            q = s.peek();
            s.pop();
            if (p == null && q == null) continue;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            s.push(p.left);
            s.push(q.left);
            s.push(p.right);
            s.push(q.right);
        }
        return true;
    }


}
