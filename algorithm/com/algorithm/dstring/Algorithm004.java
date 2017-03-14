package com.algorithm.dstring;

/**
 * Add Binary
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm004 {

    public static void main(String args[]) {

        new Solution().addBinary("11", "1");
    }

    static class Solution {

        String addBinary(String a, String b) {
            String result = "";
            int n = a.length() > b.length() ? a.length() : b.length();
            a = reverse(a);
            b = reverse(b);

            int carry = 0;
            for (int i = 0; i < n; i++) {
                int ai = i < a.length() ? a.charAt(i) - '0' : 0;
                int bi = i < b.length() ? b.charAt(i) - '0' : 0;
                int val = (ai + bi + carry) % 2;
                carry = (ai + bi + carry) / 2;
                result = val + result;
            }
            if (carry == 1) {
                result = 1 + result;
            }
            System.out.print(result);
            return result;
        }

        String reverse(String str) {
            char[] strArray = str.toCharArray();
            int len = strArray.length;
            char temp;
            for (int i = 0; i < len / 2; i++) {
                temp = strArray[i];
                strArray[i] = strArray[len - i - 1];
                strArray[len - i - 1] = temp;
            }
            return String.valueOf(strArray);
        }
    }

}
