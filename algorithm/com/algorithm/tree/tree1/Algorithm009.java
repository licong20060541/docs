package com.algorithm.tree.tree1;

import java.util.Stack;

/**
 * Symmetric Tree 对称树
 * 时间复杂度O(n) 空间复杂度O(log n)
 */
public class Algorithm009 {

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
    boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isSymmetric(root.left, root.right);
    }
    boolean isSymmetric(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val
                && isSymmetric(p.left, q.right)
                && isSymmetric(p.right, q.left);
    }


    /**
     * 迭代
     * @return
     */
    boolean isSymmetric2 (TreeNode root) {
        if (null == root) return true;
        Stack<TreeNode> s = new Stack<>();
        s.push(root.left);
        s.push(root.right);
        TreeNode p, q;
        while (!s.empty()) {
            p = s.peek();
            s.pop();
            q = s.peek();
            s.pop();
            if (p == null && q == null) return true;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            s.push(p.left);
            s.push(q.right);
            s.push(p.right);
            s.push(q.left);
        }
        return true;
    }

}
