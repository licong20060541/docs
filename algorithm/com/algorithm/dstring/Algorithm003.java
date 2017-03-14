package com.algorithm.dstring;

/**
 * String to Integer (atoi)
 * 时间复杂度O(n) 空间复杂度O(1)
 */
public class Algorithm003 {

    public static void main(String args[]) {

        System.out.println(myAtoi("-8732"));

    }


    static int myAtoi(String str) {
        int num = 0;
        int sign = 1;
        int n = str.length();
        int i = 0;
        while (str.charAt(i) == ' ' && i < n) i++;
        if (str.charAt(i) == '+') {
            i++;
        } else if (str.charAt(i) == '-') {
            sign = -1;
            i++;
        }
        for (; i < n; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9')
                break;
            if (num > Integer.MAX_VALUE / 10 ||
                    (num == Integer.MAX_VALUE / 10 &&
                            (str.charAt(i) - '0') > Integer.MAX_VALUE % 10)) {
                return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            num = num * 10 + str.charAt(i) - '0';
        }
        return num * sign;
    }


}
