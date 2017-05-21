package com.algorithm.ftree.tree2;

/**
 * Unique Binary Search Trees
 * 时间复杂度O(n^2) 空间复杂度O(n)
 二叉查找树的主要性质，就是中序遍历是有序的性质

 Given n, how many structurally unique BST’s (binary search trees) that store values 1...n?
 For example, Given n = 3, there are a total of 5 unique BST’s.

 1              3         3              2           1
  \            /         /              / \           \
   3          2         1              1   3           2
  /          /          \                               \
 2          1            2                               3

 分析

以i为根节点的树，其左子树由[1, i-1]构成，右子树由[i+1, n]构成
 f(0) = 1 空树
 f(1) = 1 单个节点
 f(2) = f(0) * f(1) // 1为根
      + f(1) * f(0) // 2为根


 f (3) = f (0) ∗ f (2) // 1
     + f (1) ∗ f (1) // 2
     + f (2) ∗ f (0) // 3

 f的递推公式 f(i) = f(k-1) * f(i-k)之和，其中k的值为[1, i]

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

    int numTrees(int n) {
        int[] f = new int[n+1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int k = 1; k <= i; ++k)
                f[i] += f[k-1] * f[i - k];
        }
        return f[n];
    }


}
