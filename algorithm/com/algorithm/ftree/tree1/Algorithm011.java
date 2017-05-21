package com.algorithm.ftree.tree1;

import java.util.Stack;

/**
 * Flatten Binary Tree to Linked List
 * 时间复杂度O(n) 空间复杂度O(log n)
 Given a binary tree, flatten it to a linked list in-place.
 For example, Given
      1
    /   \
   2     5
  / \     \
 3   4     6
 The flattened tree should look like:
 1
 \
 2
 \
 3
 \
 4
 \
 5
 \
 6
 */
public class Algorithm011 {

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
     * 递归1
     */
    class Solution {
        void flatten(TreeNode root) {
            if (root == null) return; //终止条件
            flatten(root.left);
            flatten(root.right);
            if (null == root.left) return;
            // 三方合并，将左子树所形成的链表插入到root和root->right之间
            TreeNode p = root.left;
            while(p.right != null) p = p.right; //
            p.right = root.right;
            root.right = root.left;
            root.left = null;
        }
    }
    
    
    /**
     * 递归2
     */
    class Solution2 {
        void flatten(TreeNode root) {
            flatten(root, null);
        }
        TreeNode flatten(TreeNode root, TreeNode tail) {
            if (null == root) return tail;
            root.right = flatten(root.left, flatten(root.right, tail));
            root.left = null;
            return root;
        }
    }


    /**
     * 迭代
     */
    class Solution3 {
        void flatten(TreeNode root) {
            if (root == null) return;
            Stack<TreeNode> s = new Stack<>();
            s.push(root);
            while (!s.empty()) {
                TreeNode p = s.peek();
                s.pop();
                if (p.right != null)
                    s.push(p.right);
                if (p.left != null)
                    s.push(p.left);
                p.left = null;
                if (!s.empty())
                    p.right = s.peek();
            }
        }
    }

}
