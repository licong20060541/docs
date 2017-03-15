package com.algorithm.dstring;

import java.util.List;

/**
 * Longest Common Prefix
 * 时间复杂度O(n1+n2+...)空间复杂度O(1)
 */
public class Algorithm008 {

    public static void main(String args[]) {

    }

    /**
     * 纵向扫描:逐个位置比较
     *
     * @return
     */
    String longestCommonPrefix1(List<String> strs) {
        if (strs.size() == 0) return "";
        for (int idx = 0; idx < strs.get(0).length(); ++idx) {
            for (int i = 1; i < strs.size(); ++i) {
                if (strs.get(i).charAt(idx) != strs.get(0).charAt(idx)) {
                    return strs.get(0).substring(0, idx);
                }
            }
        }
        return strs.get(0);
    }


    /**
     * 橫向比较
     *
     * @param strs
     * @return
     */
    String longestCommonPrefix2(List<String> strs) {
        if (strs.size() == 0) return "";
        int right_most = strs.get(0).length() - 1;
        for (int i = 1; i < strs.size(); i++) {
            for (int j = 0; j <= right_most; j++) {
                if (strs.get(i).charAt(j) != strs.get(0).charAt(j)) {
                    right_most = j - 1;
                }
            }
        }
        return strs.get(0).substring(0, right_most + 1);
    }

}
