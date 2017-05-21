package com.algorithm.ftree.tree3;

import java.util.ArrayList;

/**
 * Path Sum II
 * 时间复杂度O(n) 空间复杂度O(log n)
 *
 * Given a binary tree and a sum, find all root-to-leaf paths where each
 * path’s sum equals the given sum. For example: Given the below binary tree and sum = 22,
 5
 /\ 48 //\ 11 13 4 /\/\ 7251
 return
 [
 [5,4,11,2],
 [5,8,4,5] ]

 要求路径信息

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

//    class Solution {
//
//        Vector<Vector<Integer> > pathSum(TreeNode root, int sum) {
//            Vector<Vector<Integer> > result = new Vector<>();
//            Vector<Integer> cur = new Vector<>(); // 中间结果
//            pathSum(root, sum, cur, result);
//            return result;
//        }
//
//        // vector<int> &cur, vector<vector<int> > &result
//        //
//        void pathSum(TreeNode root, int gap,
//                     Vector<Integer> cur, Vector<Vector<Integer> > result) {
//            if (root == null) return;
//            cur.add(root.val);
//            if (root.left == null && root.right == null) { // leaf
//                if (gap == root.val)
//                    result.add(cur);
//            }
//            pathSum(root.left, gap - root.val, cur, result);
//            pathSum(root.right, gap - root.val, cur, result);
//            cur.remove(cur.size()-1);
//        }
//    }


    public class Solution2 {

        public ArrayList<ArrayList<TreeNode>> pathSum(TreeNode root, int sum) {
            ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
            if (root == null)
                return result;
            recursivePathSum(root, sum, new ArrayList(), result);
            return result;
        }

        private void recursivePathSum(TreeNode root, int sum, ArrayList current
                , ArrayList<ArrayList<TreeNode>> result) {
            if (root == null)
                return;
            if (root.val == sum && root.left == null && root.right == null) {
                current.add(root.val);
                result.add(new ArrayList(current)); // new
                current.remove(current.size()-1);// 比如left不满足，那么用current去测试right了
                return;
            }

            current.add(root.val);
            recursivePathSum(root.left, sum-root.val, current, result);
            recursivePathSum(root.right, sum-root.val, current, result);
            current.remove(current.size()-1); // 比如left不满足，那么用current去测试right了
        }
    }
    
}
