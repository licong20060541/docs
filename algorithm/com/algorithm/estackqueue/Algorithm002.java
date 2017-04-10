package com.algorithm.estackqueue;

import java.util.Stack;
import java.util.Vector;

/**
 * Longest Valid Parentheses
 * 时间复杂度O(n) 空间复杂度O(n)
 * <p>
 * Given a string containing just the characters '(' and ')', find the length of the longest valid
 * (well-formed) parentheses substring.
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has
 * length = 4.
 */
public class Algorithm002 {

    public static void main(String args[]) {
        System.out.print(longestValidParentheses("((()()))))"));
    }


    /**
     * 1
     * 栈
     *
     * @param s
     * @return
     */
    static int longestValidParentheses(String s) {
        int max_len = 0, last = -1; // the position of the last ')'
        Stack<Integer> lefts = new Stack<>(); // keep track of the positions of non-matching '('s
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                lefts.push(i);
            } else { // 否则就是 ')'
                if (lefts.empty()) {
                    // no matching left 表示没有匹配
                    last = i;
                } else {
                    // find a matching pair 匹配一个喽
                    lefts.pop();
                    if (lefts.empty()) {
                        max_len = Math.max(max_len, i - last);
                    } else {
                        max_len = Math.max(max_len, i - lefts.peek());
                    }
                }
            }
        }
        return max_len;
    }

    /**
     * 2
     * Dynamic Programming, One Pass
     * 利用之前的结果进行计算
     * @param s
     * @return
     *
     * test： "((()()))))"
     *
     */
    int longestValidParentheses2(String s) {

        Vector<Integer> f = new Vector<>(s.length());
        int ret = 0;

        // i = s.length() - 2, match = s.length() - 1, pass
        // i = s.length() - 3, match = s.length() - 2, pass
        // i = s.length() - 4, match = s.length() - 3, pass
        // i = s.length() - 5, match = s.length() - 4, pass
        // i = s.length() - 6, match = s.length() - 5, f(s.length() - 6) = 2
        // i = s.length() - 7, match = s.length() - 4, pass
        // i = s.length() - 8, match = s.length() - 7, pass, f(s.length() - 8) = 4
        // i = s.length() - 9, match = s.length() - 4, f(s.length() - 9) = 6
        // i = s.length() - 10, match = s.length() - 3, f(s.length() - 9) = 8
        for (int i = s.length() - 2; i >= 0; --i) {
            int match = i + f.get(i + 1) + 1;
            // case: "((...))"
            if (s.charAt(i) == '(' && match < s.length() && s.charAt(match) == ')') {
                f.set(i, f.get(i + 1) + 2);
                // if a valid sequence exist afterwards "((...))()"
                if (match + 1 < s.length()) {
                    f.set(i, f.get(i) + f.get(match + 1));
                }
            }
            ret = Math.max(ret, f.get(i));
        }
        return ret;
    }


    /**
     * 两遍扫描
     * @return
     * test： "((()()))))"
     */
    int longestValidParentheses3(String s) {
        int answer = 0, depth = 0, start = -1;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                ++depth;
            } else {
                --depth;
                if (depth < 0) {
                    start = i;
                    depth = 0;
                } else if (depth == 0) {
                    answer = Math.max(answer, i - start);
                }
            }
        }
        depth = 0;
        start = s.length();
        for (int i = s.length() - 1; i >= 0; --i) {
            if (s.charAt(i) == ')') {
                ++depth;
            } else {
                --depth;
                if (depth < 0) {
                    start = i;
                    depth = 0;
                } else if (depth == 0) {
                    answer = Math.max(answer, start - i);
                }
            }
        }
        return answer;
    }

}
