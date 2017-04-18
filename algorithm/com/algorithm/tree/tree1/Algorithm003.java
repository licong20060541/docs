package com.algorithm.tree.tree1;

import java.util.Stack;
import java.util.Vector;

/**
 * 后序遍历： 左右中
 * Binary Tree Postorder Traversal
 * 时间复杂度O(n) 空间复杂度O(n)
 */
public class Algorithm003 {

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

//    // 常见的递归方法
//    class Solution {
//        public:
//        void postOrder(TreeNode* root, vector<int> &path)
//        {
//            if(root!=null)
//            {
//                postOrder(root.left, path);
//                postOrder(root.right, path);
//                path.push_back(root.val);
//            }
//        }
//        vector<int> postorderTraversal(TreeNode *root) {
//            // IMPORTANT: Please reset any member data you declared, as
//            // the same Solution instance will be reused for each test case.
//            vector<int> path;
//            postOrder(root, path);
//            return path;
//        }
//    };


    /**
     * 栈,这个多么清晰啊
     */
    /**
     * Definition for binary tree
     * struct TreeNode {
     * int val;
     * TreeNode *left;
     * TreeNode *right;
     * TreeNode(int x) : val(x), left(null), right(null) {}
     * };
     */
    class Solution {
        Vector<Integer> postorderTraversal(TreeNode root) {
            // IMPORTANT: Please reset any member data you declared, as  
            // the same Solution instance will be reused for each test case.  
            Vector<Integer> path = new Vector<>();
            if (root == null) return path;

            Stack<TreeNode> stk = new Stack<>();
            stk.push(root);
            TreeNode cur = null;
            while (!stk.empty()) {
                cur = stk.peek();
                if (cur.left == null && cur.right == null) {
                    path.add(cur.val);
                    stk.pop();
                } else {
                    if (cur.right != null) {
                        stk.push(cur.right);
                        cur.right = null;
                    }
                    if (cur.left != null) {
                        stk.push(cur.left);
                        cur.left = null;
                    }
                }
            }
            return path;
        }
    }

    ;


    /**
     * morris 后序遍历
     * 时间复杂度O(n) 空间复杂度O(1)
     */
//    void reverse(TreeNode *from, TreeNode *to) // reverse the tree nodes 'from' . 'to'.
//    {
//        if (from == to)
//            return;
//        TreeNode *x = from, *y = from.right, *z;
//        while (true)
//        {
//            z = y.right;
//            y.right = x;
//            x = y;
//            y = z;
//            if (x == to)
//                break;
//        }
//    }
//
//    void printReverse(TreeNode* from, TreeNode *to) // print the reversed tree nodes 'from' . 'to'.
//    {
//        reverse(from, to);
//
//        TreeNode *p = to;
//        while (true)
//        {
//            printf("%d ", p.val);
//            if (p == from)
//                break;
//            p = p.right;
//        }
//
//        reverse(to, from);
//    }
//
//    void postorderMorrisTraversal(TreeNode *root) {
//        TreeNode dump(0);
//        dump.left = root;
//        TreeNode *cur = &dump, *prev = null;
//        while (cur)
//        {
//            if (cur.left == null)
//            {
//                cur = cur.right;
//            }
//            else
//            {
//                prev = cur.left;
//                while (prev.right != null && prev.right != cur)
//                    prev = prev.right;
//
//                if (prev.right == null)
//                {
//                    prev.right = cur;
//                    cur = cur.left;
//                }
//                else
//                {
//                    printReverse(cur.left, prev);  // call print
//                    prev.right = null;
//                    cur = cur.right;
//                }
//            }
//        }
//    }

}
