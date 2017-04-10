package com.algorithm.estackqueue;

import java.util.Stack;
import java.util.Vector;

/**
 * Evaluate Reverse Polish Notation
 * 时间复杂度O(n) 空间复杂度O(n)
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 Some examples:
 ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */
public class Algorithm004 {

    public static void main(String args[]) {
        Vector<String> tokens = new Vector<>();
        tokens.add("2");
        tokens.add("1");
        tokens.add("+");
        tokens.add("3");
        tokens.add("*");
        System.out.print(Solution2.evalRPN(tokens));
    }

    /**
     * 递归版本
     */
    class Solution1 {

        int evalRPN(Vector<String> tokens) {
            int x, y;
            String token = tokens.lastElement();
            tokens.remove(tokens.size() - 1);

            if (is_operator(token)) {
                y = evalRPN(tokens);
                x = evalRPN(tokens);
                if (token.charAt(0) == '+') {
                    x += y;
                } else if (token.charAt(0) == '-') {
                    x -= y;
                } else if (token.charAt(0) == '*') {
                    x *= y;
                } else {
                    x /= y;
                }
            } else {
                x = token.charAt(0) - '0';
            }
            return x;
        }
        boolean is_operator(String op) {
            return op.length() == 1 && ("+-*/").contains(op);
        }
    }


    /**
     * 迭代版
     */
    static class Solution2 {

        static int evalRPN(Vector<String> tokens) {
            Stack<String> s = new Stack<>();
            for (String token : tokens) {
                if (!is_operator(token)) {
                    s.push(token);
                } else {
                    int y = s.pop().charAt(0) - '0';
                    int x = s.pop().charAt(0) - '0';
                    if (token.charAt(0) == '+') {
                        x += y;
                    } else if (token.charAt(0) == '-') {
                        x -= y;
                    } else if (token.charAt(0) == '*') {
                        x *= y;
                    } else {
                        x /= y;
                    }
                    s.push(x + "");
                }
            }
            return s.peek().charAt(0) - '0';
        }

        static boolean is_operator(String op) {
            return op.length() == 1 && ("+-*/").contains(op);
        }

    }

}
