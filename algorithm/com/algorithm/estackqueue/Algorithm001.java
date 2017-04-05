package com.algorithm.estackqueue;

import java.util.HashMap;
import java.util.Stack;

/**
 * Valid Parentheses
 * 时间复杂度O(n) 空间复杂度O(n)
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the
 input string is valid.
 The brackets must close in the correct order, "()" and "()[]" are all valid but "(]" and "([)]" are
 not.
 */
public class Algorithm001 {

    public static void main(String args[]) {

    }

    public static boolean isValid(String s) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);

            if (map.keySet().contains(curr)) {
                stack.push(curr);
            } else if (map.values().contains(curr)) {
                if (!stack.empty() && map.get(stack.peek()) == curr) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

}
