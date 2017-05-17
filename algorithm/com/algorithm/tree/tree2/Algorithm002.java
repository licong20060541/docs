package com.algorithm.tree.tree2;

/**
 * Construct Binary Tree from Inorder and Postorder Traversal
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 *  题目：

 Given inorder and postorder traversal of a tree, construct the binary tree.

 Note:
 You may assume that duplicates do not exist in the tree.



 题解：

 这道题跟pre+in一样的方法做，只不过找左子树右子树的位置不同而已。



 1
 / \
 2   3
 / \ / \
 4  5 6  7

 对于上图的树来说，
 index: 0 1 2 3 4 5 6
 中序遍历为: 4 2 5 1 6 3 7
 后续遍历为: 4 5 2 6 7 3 1
 为了清晰表示，我给节点上了颜色，红色是根节点，蓝色为左子树，绿色为右子树。
 可以发现的规律是：
 1. 中序遍历中根节点是左子树右子树的分割点。
 2. 后续遍历的最后一个节点为根节点。

 同样根据中序遍历找到根节点的位置，然后顺势计算出左子树串的长度。在后序遍历中分割出左子树串和右子树串，
 递归的建立左子树和右子树。

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

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }

    public TreeNode buildTree(int[] in, int inStart, int inEnd, int[] post, int postStart, int postEnd){
        if(inStart > inEnd || postStart > postEnd){
            return null;
        }
        int rootVal = post[postEnd];
        int rootIndex = 0;
        for(int i = inStart; i <= inEnd; i++){
            if(in[i] == rootVal){
                rootIndex = i;
                break;
            }
        }

        int len = rootIndex - inStart;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(in, inStart, rootIndex-1, post, postStart, postStart+len-1);
        root.right = buildTree(in, rootIndex+1, inEnd, post, postStart+len, postEnd-1);

        return root;
    }

}
