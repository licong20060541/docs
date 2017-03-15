package com.algorithm.dstring;

/**
 * Wildcard Matching
 * <p>
 * Implement wildcard pattern matching with support for '?' and '*'.
 * '?' Matches any single character. '*' Matches any sequence of characters (including the empty se-
 * quence).
 * The matching should cover the entire input string (not partial).
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "*") → true
 * isMatch("aa", "a*") → true
 * isMatch("ab", "?*") → true
 * isMatch("aab", "c*a*b") → false
 */
public class Algorithm007 {

    public static void main(String args[]) {

    }


    // LeetCode, Wildcard Matching
//
//    O(n*m)
//    O(1)
//    class Solution {
//        public:
//        bool isMatch(const string& s, const string& p) {
//            return isMatch(s.c_str(), p.c_str());
//        }
//        private:
//        bool isMatch(const char *s, const char *p) {
//            bool star = false;
//            const char *str, *ptr;
//            for (str = s, ptr = p; *str != '\0'; str++, ptr++) {
//                switch (*ptr) {
//                    case '?':
//                        break;
//                    case '*':
//                        star = true;
//                        s = str, p = ptr;
//                        while (*p == '*') p++; //skip continuous '*'
//                        if (*p == '\0') return true;
//                    str = s - 1;
//                    ptr = p - 1;
//                    break;
//                    default:
//                        if (*str != *ptr) {
//                        // 如果前面没有'*'，则匹配成功
//                        if (!star) return false;
//                        s++;
//                        str = s - 1;
//                        ptr = p - 1;
//                    }
//                }
//            }
//            while (*ptr == '*') ptr++;
//            return (*ptr == '\0');
//           }
//     };


    /**
     * 仔细分析一下这样的结构。
     * p : abcd*def* ass*sss。这样的匹配实际上就是要查找字符串s中是否有如下
     * 按照一定顺序的序列：abcd、def、ass和sss。只有这四个字符串按照顺序出现，那么就匹配成功。这个很关键。
     * 这样一来，如果我们匹配到abcd*def*ass时，下一个字符是，然后把这个 继续展开，
     * 那么前面的这几个还需要进一步展开吗？当然不需要了。因为他们的目的已经实现了，
     * 就是已经找到了前几个可以匹配的字符。现在需要的就是在sIndex之后的字符串s中，
     * 找到是否有sss这个字符串，而这个匹配的工作，由最后这个 已经完全可以胜任了。
     * 那么有没有可能是后面的字符串中没有sss，而在前面出现呢？即使有可能也没用，因为还有一个重要的前提是，有序。
     * 因此这样一来的话，就可以使用迭代法。每次遇到不满足条件的时候就回溯，每次遇到*时就更新回溯点。
     */
    public class Solution {

        public boolean isMatch(String s, String p) {
            char[] chars = s.toCharArray();
            char[] charp = p.toCharArray();

            int ss = -1, pp = -1;
            int sIndex = 0, pIndex = 0;

            while (sIndex < chars.length) {
                if (pIndex == charp.length) {
                    // false，回溯, 和else一样逻辑
                    if (pp == -1) return false;
                    pIndex = pp + 1;
                    sIndex = ss++;
                } else if (charp[pIndex] == '?' || chars[sIndex] == charp[pIndex]) {//相同
                    pIndex++;
                    sIndex++;
                } else if (charp[pIndex] == '*') {
                    pp = pIndex;
                    ss = sIndex;
                    pIndex = pp + 1;
                } else {
                    // 假如p的值abcd*xef*，对应的为s的值abcxxdef，则第一次会从s的第一个x开始向后比较
                    // 发现不满足，则从s的第二个x开始比较，依次...
                    if (pp == -1) return false;
                    pIndex = pp + 1;
                    sIndex = ss++;
                }
            }
            while (pIndex < charp.length) {
                if (charp[pIndex] != '*')
                    break;
                pIndex++;
            }
            return pIndex == charp.length;
        }

    }

}
