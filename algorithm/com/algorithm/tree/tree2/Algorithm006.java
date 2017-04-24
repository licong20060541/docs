package com.algorithm.tree.tree2;

import java.util.Vector;

/**
 * Convert Sorted Array to Binary Search Tree
 * 时间复杂度O(n) 空间复杂度O(log n)
 * 二分法
 * 给定一个区间[left, right]，取其中值mid=(left+right)/2对应的元素作为二叉树的根。
 * 二叉树的左子树根的值为对[left, mid-1]继续操作的结果，右子树类似。
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

    TreeNode generateBST(int left, int right, Vector<Integer> num) {
        if (left > right)
            return null;
        else if (left == right)
            return new TreeNode(num.get(left));
        else {
            Integer mid = (left + right) / 2;
            TreeNode node = new TreeNode(num.get(mid));
            node.left = generateBST(left, mid - 1, num);
            node.right = generateBST(mid + 1, right, num);
            return node;
        }
    }

    TreeNode sortedArrayToBST(Vector<Integer> num) {
        return generateBST(0, num.size() - 1, num);
    }

}
