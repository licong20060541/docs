package com.algorithm.tree.tree1;

import java.util.ArrayList;

/**
 *  Recover Binary Search Tree -- LeetCode
 这道题是要求恢复一颗有两个元素调换错了的二叉查找树。一开始拿到可能会觉得比较复杂，其实观察出规律了就比较简单。
 主要还是利用二叉查找树的主要性质，就是中序遍历是有序的性质。
 那么如果其中有元素被调换了，意味着中序遍历中必然出现违背有序的情况。那么会出现几次呢？有两种情况，
 如果是中序遍历相邻的两个元素被调换了，很容易想到就只需会出现一次违反情况，只需要把这个两个节点记录下来最后调换值就可以；
 如果是不相邻的两个元素被调换了，举个例子很容易可以发现，会发生两次逆序的情况，
 那么这时候需要调换的元素应该是第一次逆序前面的元素，和第二次逆序后面的元素。
 比如1234567,1和5调换了，会得到5234167，逆序发生在52和41，
 我们需要把4和1调过来，那么就是52的第一个元素，41的第二个元素调换即可。
 搞清楚了规律就容易实现了，中序遍历寻找逆序情况，调换的第一个元素，永远是第一个逆序的第一个元素，
 而调换的第二个元素如果只有一次逆序，则是那一次的后一个，如果有两次逆序则是第二次的后一个。
 算法只需要一次中序遍历，
 所以时间复杂度是O(n)，空间是栈大小O(logn)。
 */
public class Algorithm007 {

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


    public void recoverTree(TreeNode root) {
        if(root == null)
            return;
        ArrayList<TreeNode> pre = new ArrayList<TreeNode>();
        pre.add(null);
        ArrayList<TreeNode> res = new ArrayList<TreeNode>();
        helper(root,pre, res);
        if(res.size()>0)
        {
            int temp = res.get(0).val;
            res.get(0).val = res.get(1).val;
            res.get(1).val = temp;
        }
    }

    /**
     * 递归
     * @param root
     * @param pre
     * @param res
     */
    private void helper(TreeNode root, ArrayList<TreeNode> pre, ArrayList<TreeNode> res)
    {
        if(root == null)
        {
            return;
        }
        // 中序遍历
        helper(root.left, pre, res);
        if(pre.get(0)!=null && pre.get(0).val>root.val)
        {
            if(res.size()==0)
            {
                res.add(pre.get(0));
                res.add(root);
            }
            else
            {
                res.set(1,root);
            }
        }
        pre.set(0,root);
        helper(root.right,pre,res);
    }


    /**
     * Morris
     * 空间O(1)
     */
//    class Solution {
//        void recoverTree(TreeNode* root) {
//            pair<TreeNode*, TreeNode*> broken;
//            TreeNode* prev = nullptr;
//            TreeNode* cur = root;
//            while (cur != nullptr) {
//                if (cur->left == nullptr) {
//                    detect(broken, prev, cur);
//                    prev = cur;
//                    cur = cur->right;
//                } else {
//                    auto node = cur->left;
//                    while (node->right != nullptr && node->right != cur)
//                        node = node->right;
//                    if (node->right == nullptr) {
//                        node->right = cur;
//                        //prev = cur;
//                        cur = cur->left;
//                    } else {
//                        detect(broken, prev, cur);
//                        node->right = nullptr;
//                        prev = cur;
//                        cur = cur->right;
//                    }
//                }
//            }
//            swap(broken.first->val, broken.second->val);
//        }
//        void detect(pair<TreeNode*, TreeNode*>& broken, TreeNode* prev,
//                    TreeNode* current) {
//            if (prev != nullptr && prev->val > current->val) {
//                if (broken.first == nullptr) {
//                    broken.first = prev;
//                } //不能用else，如{0, 1},会导致swap时second为null
//                broken.second = current;
//            }
//        }
//    }

}
