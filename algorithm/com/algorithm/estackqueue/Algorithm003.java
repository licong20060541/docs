package com.algorithm.estackqueue;

import java.util.Stack;
import java.util.Vector;

/**
 * Largest Rectangle in Histogram
 * 时间复杂度O(n) 空间复杂度O(n)
 * Given n non-negative integers representing the histogram’s bar height where the width of each bar is 1,
 find the area of largest rectangle in the histogram.
 Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
 The largest rectangle is shown in the shaded area, which has area = 10 unit.
 For example, Given height = [2,1,5,6,2,3], return 10.
 */
public class Algorithm003 {

    public static void main(String args[]) {
        Vector<Integer> datas = new Vector<>();
        datas.add(2);
        datas.add(1);
        datas.add(5);
        datas.add(6);
        datas.add(2);
        datas.add(3);
        System.out.println(new Algorithm003().largestRectangleArea(datas));
    }

    int largestRectangleArea(Vector<Integer> height) {
        Stack<Integer> s = new Stack<>();
        height.add(0);
        int result = 0;
        // i = 0, s = 0
        // i = 1, s = null, result = 2, s = 1
        // i = 2, s = 1-2
        // i = 3, s = 1-2-3
        // i = 4, s = 1-2, tmp = 3, result = 6*1 = 6;
        //        s = 1, tmp = 2, result = 5*2 = 10;
        //        s = 1-2, tmp = 1, result = 10;
        // i = 5, s = 1-2-3, ... ,
        for (int i = 0; i < height.size(); ) {
            if (s.empty() || height.get(i) > height.get(s.peek())) {
                // 为空或者遇到大的才入栈
                s.push(i++); //只有这里加一操作
            } else {
                // 遇到小的开始依次处理栈里的内容
                int tmp = s.peek();
                s.pop();
                // 最后留下来的肯定是最小的，因此 height.get(tmp) * i
                result = Math.max(result,
                        height.get(tmp) * (s.empty() ? i : i - s.peek() - 1));
                System.out.println(result);
            }
        }
        return result;
    }

}
