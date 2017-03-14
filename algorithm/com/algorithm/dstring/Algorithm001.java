package com.algorithm.dstring;

/**
 * Valid Palindrome
 * 时间复杂度O(n) 空间复杂度O(1)
 *
 * 验证回文字符串是比较常见的问题，所谓回文，就是一个正读和反读都一样的字符串，
 * 比如“level”或者“noon”等等就是回文串。但是这里，加入了空格和非字母数字的字符，
 * 增加了些难度，但其实原理还是很简单：只需要建立两个指针，left和right,
 * 分别从字符的开头和结尾处开始遍历整个字符串，如果遇到非字母数字的字符就跳过，
 * 继续往下找，直到找到下一个字母数字或者结束遍历，如果遇到大写字母，就将其转为小写。
 * 等左右指针都找到字母数字时，比较这两个字符，若相等，则继续比较下面两个分别找到的字母数字，
 * 若不相等，直接返回false.
 *
 */
public class Algorithm001 {

    public static void main(String args[]) {

    }

    class Solution {

        boolean isPalindrome(String s) {
            int left = 0, right = s.length() - 1 ;
            while (left < right) {
                if (!isAlphaNum(s.charAt(left))) {
                    ++left;
                } else if (!isAlphaNum(s.charAt(right))) {
                    --right;
                } else if (s.charAt(left) != s.charAt(right)) {
                    return false;
                } else {
                    ++left;
                    --right;
                }
            }
            return true;
        }

        boolean isAlphaNum(char ch) {
            if (ch >= 'a' && ch <= 'z') return true;
            if (ch >= 'A' && ch <= 'Z') return true;
            if (ch >= '0' && ch <= '9') return true;
            return false;
        }
    }

}
