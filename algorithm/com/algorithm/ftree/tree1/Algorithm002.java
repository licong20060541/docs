package com.algorithm.ftree.tree1;

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
     *
     *
     * MorrisInOrder()：
     while 没有结束
     如果当前节点没有左后代
        访问该节点
        转向右节点
     否则
        找到左后代的最右节点，且使最右节点的右指针指向当前节点
        转向左后代节点
     *
     */

    // java version
    public class MorrisTree {
        public void main(String[] args) {
            Tree t = new Tree();
            t = t.make();
            t.printTree();
        }
    }

    static class Tree{
        private Node root;
        private class Node{
            private String value;
            private Node left, right;
            public Node(String value, Node left, Node right) {
                this.value = value;
                this.left = left;
                this.right = right;
            }
        }

        public Tree make() {
            Tree t = new Tree();
            t.root = new Node("F", null, null);
            t.root.left = new Node("B", new Node("A", null, null),
                    new Node("D", new Node("C", null, null), new Node("E", null, null)));
            t.root.right = new Node("G", null, new Node("I", new Node("H", null, null), null));
            return t;
        }

        void printTree() {
            Tree t = make();
            System.out.println("普通中序遍历结果：");
            middleOrder(t.root);
            System.out.println();
            System.out.println("Morris中序遍历结果：");
            morris_inorder(t.root);
            //middleOrder(t.root);
        }

        private void middleOrder(Node root) {
            if(root.left != null)
                middleOrder(root.left);
            System.out.print(root.value + "  ");
            if(root.right != null)
                middleOrder(root.right);
        }


        public void morris_inorder(Node root) {
            while(root != null) {
                if(root.left != null) {
                    Node temp = root.left;
                    while(temp.right != null && temp.right != root) {
                        temp = temp.right;
                    }
                    if(temp.right == null) {
                        temp.right = root;
                        root = root.left;
                    } else {
                        System.out.print(root.value + " ");
                        temp.right = null;
                        root = root.right;
                    }
                } else {
                    System.out.print(root.value + " ");
                    root = root.right;
                }
            }

        }

    }



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
