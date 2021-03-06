package com.algorithm.hbaolimeiju;

import java.util.ArrayList;
import java.util.List;

/**
 * Permutations
 * Given a collection of numbers, return all possible permutations.
 For example, [1,2,3] have the following permutations:
 [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 这道题跟N-Queens，Sudoku Solver，Combination Sum，Combinations等一样，也是一个NP问题。
 方法还是原来那个套路，还是用一个循环递归处理子问题。区别是这里并不是一直往后推进的，前面的数有可能放到后面，
 所以我们需要维护一个used数组来表示该元素是否已经在当前结果中，因为每次我们取一个元素放入结果，
 然后递归剩下的元素，所以不会出现重复。时间复杂度还是NP问题的指数量级。代码如下：

 */
public class Algorithm003 {

//    class Solution { // O(n!) O(1)
//        public:
//        vector<vector<int> > permute(vector<int> &num) {
//            vector<vector<int> > result;
//            sort(num.begin(), num.end());
//            do {
//                result.push_back(num);
//                //      调用   1.12题目    next_permutation()
//            } while(next_permutation(num.begin(), num.end())); return result;
//        } };



    // 解题思路：字符交换加dfs。dfs:深度优先搜索  O(n!) O(n)
    // 将第0个字符和从第0开始的每个字符进行交换，对于交换后的结果，再从第1个字符开始交换。一直到最后一个字符。
    // AC代码   accept


    ////////////////////

    public static void Permutations(int[] a, int begin, List<List<Integer>> result) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int n = a.length, tmp;
        if (begin == n) {
            for (int i = 0; i < n; i++)
                list.add(a[i]);
            result.add(list);
        } else {
            for (int i = begin; i < n; i++) {
                tmp = a[begin];
                a[begin] = a[i];
                a[i] = tmp;
                Permutations(a, begin + 1, result);
                tmp = a[begin];
                a[begin] = a[i];
                a[i] = tmp;
            }
        }
    }

    public static List<List<Integer>> permute2(int[] nums) {
        if (nums == null || nums.length == 0)
            return new ArrayList<List<Integer>>();
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Permutations(nums, 0, result);

        return result;
    }





    // 这道题跟N-Queens，Sudoku Solver，Combination Sum，Combinations等一样，也是一个NP问题。
    // 方法还是原来那个套路，还是用一个循环递归处理子问题。区别是这里并不是一直往后推进的，
    // 前面的数有可能放到后面，所以我们需要维护一个used数组来表示该元素是否已经在当前结果中，
    // 因为每次我们取一个元素放入结果，然后递归剩下的元素，所以不会出现重复。时间复杂度还是NP问题的指数量级。
    // 代码如下：
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if(num==null || num.length==0)
            return res;
        helper(num, new boolean[num.length], new ArrayList<Integer>(), res);
        return res;
    }
    private void helper(int[] num, boolean[] used, ArrayList<Integer> item, ArrayList<ArrayList<Integer>> res)
    {
        if(item.size() == num.length)
        {
            res.add(new ArrayList<Integer>(item));
            return;
        }
        for(int i=0;i<num.length;i++)
        {
            if(!used[i])
            {
                used[i] = true;
                item.add(num[i]);
                helper(num, used, item, res);
                item.remove(item.size()-1);
                used[i] = false;
            }
        }
    }
    // 注意在实现中有一个细节，就是在递归函数的前面，我们分别设置了used[i]的标记，
    // 表明改元素被使用，并且把元素加入到当前结果中，而在递归函数之后，
    // 我们把该元素从结果中移除，并把标记置为false，这个我们可以称为“保护现场”，
    // 递归函数必须保证在进入和离开函数的时候，变量的状态是一样的，这样才能保证正确性。
    // 当然也可以克隆一份结果放入递归中，但是那样会占用大量空间。所以个人认为这种做法是最高效的，
    // 而且这种方法在很多题目（几乎所有NP问题）中一直用到，不熟悉的朋友可以练习一下哈。
    // 对于NP问题我们一直都是用递归方法，也是一个很成熟的套路，可以举一反三。
    // 不过有时候面试官会刻意让我们实现一下迭代的做饭，所以这里我们就介绍一下迭代的实现方法。
    // 迭代一般就是要理清每次加入一个新的元素，我们应该做什么，这里其实还是比较清晰的，
    // 假设有了当前前i个元素的所有permutation，当第i+1个元素加进来时，
    // 我们要做的就是将这个元素带入每一个之前的结果，并且放到每一个结果的各个位置中。
    // 因为之前的结果没有重复，所以带入新元素之后也不会有重复（当然这里假设数字集合本身是没有重复的元素的）。
    // 按照这个思路，实现的代码如下：
    public ArrayList<ArrayList<Integer>> permute3(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if(num == null || num.length==0)
            return res;
        ArrayList<Integer> first = new ArrayList<Integer>();
        first.add(num[0]);
        res.add(first);
        for(int i=1;i<num.length;i++)
        {
            ArrayList<ArrayList<Integer>> newRes = new ArrayList<ArrayList<Integer>>();
            for(int j=0;j<res.size();j++)
            {
                ArrayList<Integer> cur = res.get(j);
                for(int k=0;k<cur.size()+1;k++)
                {
                    ArrayList<Integer> item = new ArrayList<Integer>(cur);
                    item.add(k,num[i]);
                    newRes.add(item);
                }
            }
            res = newRes;
        }
        return res;
    }
    // 上面的代码有时候乍一看可能觉得只有两层循环，时间复杂度是不是O(n^2)之类的。
    // 事实上肯定不是的，因为注意第二层循环是对于res进行遍历的，而res是一直在以平方量级增长的，
    // 所以总的时间复杂度还是会是指数量级以上的。
    // 这种NP问题的求解在LeetCode中非常常见，类似的有N-Queens，Sudoku Solver，
    // Combination Sum，Combinations，不过思路差不多，掌握套路就不难了。
    // 这道题还有一个扩展就是如果元素集合中会出现重复，那么意味着我们需要跳过一些重复元素，
    // 具体的细节可以参见Permutations II。

}
