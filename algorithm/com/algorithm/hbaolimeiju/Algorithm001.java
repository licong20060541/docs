package com.algorithm.hbaolimeiju;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Subsets
 * <p>
 * Given a set of distinct integers, S, return all possible subsets.
 * Note:
 * • Elements in a subset must be in non-descending order.
 * • The solution set must not contain duplicate subsets.
 * For example, If S = [1,2,3], a solution is: [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 * <p>
 * 给出一个数组生成该数组所有元素的组合。
 * 基本思路循环+dfs，生成指定元素数目（0，1,2,...array.size()个元素）的组合。
 * 1题即本题目和2题的区别在于2中允许数组中出现重复的元素。所以2在dfs的时候要跳过重复的元素，
 * 例如：[1,1,2]  如果不加跳过处理的话，生成的子集会有两个：[1,2]，但[1,1,2]是一个合理的组合。
 */
public class Algorithm001 {

    public static void main(String args[]) {

    }


    /**
     * 经典，认真思考，带入案例
     */
    public class Solution {
        void dfs(int[] number_array, int start, int number, ArrayList<Integer> array
                , ArrayList<ArrayList<Integer>> result) {
            if (number == array.size()) {
                result.add(new ArrayList<>(array));
                return;
            }
            for (int i = start; i < number_array.length; i++) {
                array.add(number_array[i]);
                dfs(number_array, i + 1, number, array, result);
                array.remove(array.size() - 1);
            }
        }

        public ArrayList<ArrayList<Integer>> subsets(int[] S) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();
            ArrayList<Integer> array = new ArrayList<>();
            result.add(array);
            if (S == null) {
                return result;
            }
            Arrays.sort(S);
            for (int i = 1; i <= S.length; i++) { // i表示有几个元素为一组
                array.clear();
                dfs(S, 0, i, array, result);
            }
            return result;
        }
    }


    // 递归  时间复杂度O(2^n) 空间复杂度O(n)
    // 1 增量构造法，深搜
    // 2 位向量法，深搜


    // 迭代
    // 1 增量构造法 时间复杂度O(2^n) 空间复杂度O(1)
    // 2 二进制法


}
