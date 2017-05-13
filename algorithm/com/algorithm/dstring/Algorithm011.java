package com.algorithm.dstring;

/**
 * Roman to Integer
 * 时间复杂度O(n) 空间复杂度O(1)
 *
 * 从前往后扫描，用一个临时变量记录分段数字，如果当前比前一个大，说明这一段的值应该是当前这个值
 * 减去上一个值。比如 IV = 5 – 1 ；
 * 否则将当前值加入到结果中，然后开始下一段记录，比如 VI = 5 + 1, II=1+1
 *
 */
public class Algorithm011 {

    public static void main(String args[]) {

    }

    int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && map(s.charAt(i)) > map(s.charAt(i - 1))) {
                result += (map(s.charAt(i)) - 2 * map(s.charAt(i - 1)));
            } else {
                result += map(s.charAt(i));
            }
        }
        return result;
    }

    int map(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

}
