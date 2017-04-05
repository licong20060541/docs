package com.algorithm.dstring;

/**
 * Length of Last Word
 * 时间复杂度O(n) 空间复杂度O(1)
 *
 * For example, Given s = "Hello World", return 5.
 *
 */
public class Algorithm015 {

    public static void main(String args[]) {

    }

    int lengthOfLastWord(String s) {
        int len = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i++) != ' ') {
                ++len;
            } else if (i < s.length() && s.charAt(i) != ' '){
                len = 0;
            }
        }
        return len;
    }

}
