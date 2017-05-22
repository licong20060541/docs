package com.algorithm.hbaolimeiju;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Subsets II
 * <p>
 * Given a collection of integers that might contain duplicates, S, return all possible subsets.
 * Note:
 * Elements in a subset must be in non-descending order. The solution set must not contain duplicate
 * subsets. For example, If S = [1,2,2], a solution is: [
 * [2],
 * [1],
 * [1,2,2],
 * [2,2],
 * [1,2],
 * []
 * ]
 * <p>
 * 给出一个数组生成该数组所有元素的组合。
 * 基本思路循环+dfs，生成指定元素数目（0，1,2,...array.size()个元素）的组合。
 * 1题即本题目和2题的区别在于2中允许数组中出现重复的元素。所以2在dfs的时候要跳过重复的元素，
 * 例如：[1,1,2]  如果不加跳过处理的话，生成的子集会有两个：[1,2]，但[1,1,2]是一个合理的组合。
 */
public class Algorithm002 {

    public static void main(String args[]) {

    }

    public class Solution {
        void dfs(int[] number_array, int start, int number, ArrayList<Integer> array
                , ArrayList<ArrayList<Integer>> result) {
            if (array.size() == number) {
                result.add(new ArrayList<>(array));
                return;
            }
            int i = start;
            while (i < number_array.length) {
                array.add(number_array[i]);
                dfs(number_array, i + 1, number, array, result);
                array.remove(array.size() - 1);
                //跳过相同的元素
                while (i < (number_array.length - 1) && number_array[i] == number_array[i + 1]) {
                    i++;
                }
                i++;
            }
        }


        public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> array = new ArrayList<Integer>();
            result.add(array);
            if (num == null) {
                return result;
            }
            Arrays.sort(num);
            for (int i = 1; i <= num.length; i++) {
                array.clear();
                dfs(num, 0, i, array, result);
            }
            return result;
        }
    }


}
