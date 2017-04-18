package com.algorithm.tree.tree1;

import java.util.Stack;
import java.util.Vector;

/**
 * 中序遍历： 左中右
 * Binary Tree Inorder Traversal
 * 时间复杂度O(n) 空间复杂度O(n)
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

    /**
     * 栈
     */
    public class Solution {

        public Vector<Integer> preorderTraversal(TreeNode root) {
            Vector<Integer> result = new Vector<>();
            Stack<TreeNode> s = new Stack<>();
            TreeNode p = root;
            while (!s.empty() || p != null) {
                if (p != null) {
                    s.push(p);
                    p = p.left;
                } else {
                    p = s.peek();
                    s.pop();
                    result.add(p.val);
                    p = p.right;
                }
            }
            return result;
        }
    }


    /**
     * morris
     * 时间复杂度O(n) 空间复杂度O(1)
     */
//    void inorderMorrisTraversal(TreeNode *root) {
//        TreeNode *cur = root, *prev = NULL;
//        while (cur != NULL)
//        {
//            if (cur->left == NULL)          // 1.
//            {
//                printf("%d ", cur->val);
//                cur = cur->right;
//            }
//            else
//            {
//                // find predecessor
//                prev = cur->left;
//                while (prev->right != NULL && prev->right != cur)
//                    prev = prev->right;
//
//                if (prev->right == NULL)   // 2.a)
//                {
//                    prev->right = cur;
//                    cur = cur->left;
//                }
//                else                       // 2.b)
//                {
//                    prev->right = NULL;
//                    printf("%d ", cur->val);
//                    cur = cur->right;
//                }
//            }
//        }
//    }

}
