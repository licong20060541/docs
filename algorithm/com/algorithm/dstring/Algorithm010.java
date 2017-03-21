package com.algorithm.dstring;

/**
 * Integer to Roman
 * 时间复杂度O(n) 空间复杂度O(1)
 *Given an integer, convert it to a roman numeral.
 Input is guaranteed to be within the range from 1 to 3999.
 *
 */
public class Algorithm010 {

    public static void main(String args[]) {

    }

    String intToRoman(int num) {

        int radix[] = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        String symbol[] = {"M", "CM", "D", "CD", "C", "XC",
                "L", "XL", "X", "IX", "V", "IV", "I"};

        String roman = "";

        for (int i = 0; num > 0; ++i) {
            int count = num / radix[i];
            num %= radix[i];
            for (; count > 0; --count) roman += symbol[i];
        }
        return roman;
    }

}
