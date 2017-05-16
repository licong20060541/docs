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
     * 栈
     */
    class Solution {
        Vector<Integer> postorderTraversal(TreeNode root) {

            Vector<Integer> result = new Vector<>();
            Stack<TreeNode> s = new Stack<>();
            TreeNode p = root, q = null;
            do { // 大循环

                while (p != null) { /*   往左下走   */
                    s.push(p);
                    p = p.left;
                }

                q = null;
                while (!s.empty()) {
                    p = s.peek();
                    s.pop();
                    /*   右孩子不存在，或已被访问  */
                    if (p.right == q) {
                        result.add(p.val);
                        q = p; /*    保存刚访问过的节点       */
                    } else {
                        /*        二次进栈         */
                        s.push(p);
                        /*   先处理右子树     */
                        p = p.right;
                        break;
                    }
                }

            } while (!s.empty());

            return result;
        }
    }





    /**
     * morris 后序遍历
     * 时间复杂度O(n) 空间复杂度O(1)
     *
     * from到to的翻转
     *
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
//        reverse(from, to); // 翻转
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
//        reverse(to, from); // 恢复
//    }
//    // 套一个简单例子： root 1，  left  2，  right  3
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
