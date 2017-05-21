package com.algorithm.ftree.tree3;

import java.util.LinkedList;

/**
 * Minimum Depth of Binary Tree
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * 二叉树的最小深度为根节点到最近叶子节点的距离。
 *
 * Given a binary tree, find its minimum depth.
 The minimum depth is the number of nodes along the
 shortest path from the root node down to the nearest leaf node.
 其实跟Maximum Depth of Binary Tree非常类似，只是这道题因为是判断最小深度，
 所以必须增加一个叶子的判断（因为如果一个节点如果只有左子树或者右子树，
 我们不能取它左右子树中小的作为深度，因为那样会是0，我们只有在叶子节点才能判断深度，
 而在求最大深度的时候，因为一定会取大的那个，所以不会有这个问题）。
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
     * 递归
     */
//    class Solution {
//
//        int minDepth(TreeNode root) {
//            return minDepth(root, false);
//        }
//
//        int minDepth(TreeNode root, boolean hasbrother) {
//            if (root == null)
//                return hasbrother ? Integer.MAX_VALUE : 0;
//            return 1 + Math.min(
//                            minDepth(root.left, root.right != null),
//                            minDepth(root.right, root.left != null));
//        }
//    }
    public int minDepth(TreeNode root) {
        if(root == null)
            return 0;
        if(root.left == null)
            return minDepth(root.right)+1;
        if(root.right == null)
            return minDepth(root.left)+1;
        return Math.min(minDepth(root.left),minDepth(root.right))+1;
    }


    /**
     * 迭代
     */
//    class Solution2 {
//        int minDepth(TreeNode root) {
//            if (root == null)
//                return 0;
//            int result = Integer.MAX_VALUE;
//            Stack<pair<TreeNode, Integer>> s;
//            s.push(make_pair(root, 1));
//
//            while (!s.empty()) {
//                // 取出
//                TreeNode node = s.top().first;
//                int depth = s.top().second;
//                s.pop();
//
//                if (node.left == null && node.right == null) {
//                    result = Math.min(result, depth);
//                } else {
//                    if (node.left != null && result > depth) //
//                        s.push(make_pair(node.left, depth + 1));
//                    if (node.right != null && result > depth)
//                        s.push(make_pair(node.right, depth + 1));
//                }
//            }
//            return result;
//        }
//    }
    public int minDepth2(TreeNode root) {
        if(root == null)
            return 0;
        LinkedList queue = new LinkedList();
        int curNum = 0;
        int lastNum = 1;
        int level = 1;
        queue.offer(root);
        while(!queue.isEmpty())
        {
            TreeNode cur = (TreeNode) queue.poll();

            // 叶子节点
            if(cur.left==null && cur.right==null)
                return level;

            lastNum--;

            if(cur.left!=null)
            {
                queue.offer(cur.left);
                curNum++;
            }
            if(cur.right!=null)
            {
                queue.offer(cur.right);
                curNum++;
            }

            if(lastNum==0)
            {
                lastNum = curNum;
                curNum = 0;
                level++;
            }
        }
        return 0;
    }



}
