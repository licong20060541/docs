package com.algorithm.ftree.tree1;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * 树的遍历有两类： 深度优先和宽度优先， 深度优先分为先根遍历和后根遍历
 * 但二叉树还有个一般树没有的叫中序遍历：left--root--right
 *
 *
 *
 * 前序遍历： 中左右
 * Binary Tree Preorder Traversal
 * 时间复杂度O(n) 空间复杂度O(n)
 */
public class Algorithm001 {

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

        public ArrayList<Integer> preorderTraversal(TreeNode root) {
            ArrayList<Integer> returnList = new ArrayList<>();

            if (root == null)
                return returnList;

            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);

            while (!stack.empty()) {
                TreeNode n = stack.pop();
                returnList.add(n.val);

                if (n.right != null) {
                    stack.push(n.right);
                }
                if (n.left != null) {
                    stack.push(n.left);
                }

            }
            return returnList;
        }
    }


    /**
     * morris 先序遍历
     * 时间复杂度O(n) 空间复杂度O(1)
     */
//    void preorderMorrisTraversal(TreeNode *root) {
//        TreeNode *cur = root, *prev = NULL;
//        while (cur != NULL)
//        {
//            if (cur->left == NULL)
//            {
//                printf("%d ", cur->val);
//                cur = cur->right;
//            }
//            else
//            {
//                prev = cur->left;
//                while (prev->right != NULL && prev->right != cur)
//                    prev = prev->right;
//
//                if (prev->right == NULL)
//                {
//                    printf("%d ", cur->val);  // the only difference with inorder-traversal
//                    prev->right = cur;
//                    cur = cur->left;
//                }
//                else
//                {
//                    prev->right = NULL;
//                    cur = cur->right;
//                }
//            }
//        }
//    }

}
