package com.algorithm.alist;

import java.util.HashMap;

/**
 * 查找连续数字
 * 时间复杂度O(n) 空间复杂度O(n)
 */
public class Algorithm006 {

    public static void main(String args[]) {

//        Method1();
        Method2();

    }

    private static void Method1() {
        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        HashMap<Integer, Boolean> numMap = new HashMap<>();

        for (int num : nums) {
            numMap.put(num, false);
        }

        int longest = 0;

        for (int num : nums) {
            if (!numMap.get(num)) {
                int length = 1;
                numMap.put(num, true);
                for (int j = num + 1; numMap.containsKey(j); j++) {
                    numMap.put(j, true);
                    length++;
                }
                for (int j = num - 1; numMap.containsKey(j); j--) {
                    numMap.put(j, true);
                    length++;
                }
                longest = Math.max(longest, length);
            }

        }
        System.out.println("result: " + longest);
    }


    /**
     * 聚类
     */
    private static void Method2() {
//        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        int[] nums = new int[]{4, 5, 6, 7, 8, 3};
        HashMap<Integer, Integer> map = new HashMap<>();
        int l = 1;
        for (int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
                if(map.containsKey(nums[i] - 1)) {
                    l = Math.max(l, mergeCluster(map, nums[i] - 1, nums[i]));
                }
                if(map.containsKey(nums[i] + 1)) {
                    l = Math.max(l, mergeCluster(map, nums[i], nums[i] + 1));
                }
            }
        }
        System.out.println("result: " + l);

    }

    private static int mergeCluster(HashMap<Integer, Integer> map, int left, int right) {
        // 先求出左右边界数值
        // 既然作为右值出现，则其左侧注定为空，否则说明之前有个相同的数来过，这明显不可能，因此if屏蔽了
        // 求出其连续的最大右值
        int upper = right + map.get(right) - 1;
        // 作为左值出现，则其右侧注定为空
        int lower = left - map.get(left) + 1;
        int length = upper - lower + 1; // right - left + map.get(right) + map.get(left) - 1
        // 根据调用可知， right - left = 1, 因此 length = map.get(right) + map.get(left)
        map.put(upper, length); // 只对边界值作记录，而中间值均忽略
        map.put(lower, length);
        return length;
    }

}
