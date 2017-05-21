package com.algorithm.ftree.tree2;

import java.util.Vector;

/**
 * Unique Binary Search Trees 2
 二叉查找树的主要性质，就是中序遍历是有序的性质

 Given n, generate all structurally unique BST’s (binary search trees) that store values 1...n.
 For example, Given n = 3, your program should return all 5 unique BST’s shown below.

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



 1. 每一次都在一个范围内随机选取一个结点作为根。
 2. 每选取一个结点作为根，就把树切分成左右两个子树，直至该结点左右子树为空。

 大致思路如上，可以看出这也是一个可以划分成子问题求解的题目，所以考点是动态规划。
 但具体对于本题来说，采取的是自底向上的求解过程。
 1. 选出根结点后应该先分别求解该根的左右子树集合，也就是根的左子树有若干种，它们组成左子树集合，
 根的右子树有若干种，它们组成右子树集合。
 2. 然后将左右子树相互配对，每一个左子树都与所有右子树匹配，每一个右子树都与所有的左子树匹配。
 然后将两个子树插在根结点上。
 3. 最后，把根结点放入链表中。

 */
public class Algorithm004 {

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
    
    class Solution {
        Vector<TreeNode> generateTrees(int n) {
            if (n == 0) return generate(1, 0);
            return generate(1, n);
        }
        Vector<TreeNode> generate(int start, int end) {
            Vector<TreeNode> subTree = new Vector<>();
            if (start > end) {
                subTree.add(null);
                return subTree;
            }
            for (int k = start; k <= end; k++) {
                Vector<TreeNode> leftSubs = generate(start, k - 1);
                Vector<TreeNode> rightSubs = generate(k + 1, end);
                for (TreeNode i : leftSubs) {
                    for (TreeNode j : rightSubs) {
                        TreeNode node = new TreeNode(k);
                        node.left = i;
                        node.right = j;
                        subTree.add(node);
                    }
                }
            }
            return subTree;
        }
    }


}
