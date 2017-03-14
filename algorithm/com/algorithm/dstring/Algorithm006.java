package com.algorithm.dstring;

/**
 * Regular Expression Matching
 * 时间复杂度O(n) 空间复杂度O(1)
 * <p>
 * Implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 */
public class Algorithm006 {

/**
    首先要理解题意:
    "a"对应"a", 这种匹配不解释了;
    任意字母对应".", 这也是正则常见;
    0到多个相同字符x,对应"x*", (重要！！理解！！)比起普通正则,
    这个地方多出来一个前缀x. x代表的是 相同的字符中取一个,比如"aaaab"对应是"a*b"
            "*"还有一个易于疏忽的地方就是它的"贪婪性"要有一个限度.比如"aaa"对应"a*a",
    代码逻辑不能一路贪婪到底
    正则表达式如果期望着一个字符一个字符的匹配,是非常不现实的.而"匹配"这个问题,
    非 常容易转换成"匹配了一部分",整个匹配不匹配,要看"剩下的匹配"情况.
    这就很好的把 一个大的问题转换成了规模较小的问题:递归
    确定了递归以后,使用java来实现这个问题,会遇到很多和c不一样的地方,
    因为java对字符 的控制不像c语言指针那么灵活charAt一定要确定某个位置存在才可以使用.
    如果pattern是"x*"类型的话,那么pattern每次要两个两个的减少.
            否则,就是一个一个 的减少. 无论怎样减少,都要保证pattern有那么多个.
            比如s.substring(n), 其中n 最大也就是s.length()
*/


    public static void main(String args[]) {

    }

//    boolean isMatch(const char *s, const char *p) {
//        if (*p == '\0'){
//            return*s == '\0';
//        }
//// next char is not '*', then must match current character
//        if (*(p + 1) != '*'){
//            if (*p ==*s || ( * p == '.' &&*s != '\0'))
//                return isMatch(s + 1, p + 1);
//            else
//                return false;
//        } else { // next char is '*'
//            while (*p ==*s || ( * p == '.' &&*s != '\0')){
//                if (isMatch(s, p + 2))
//                    return true;
//                s++;
//            }
//            return isMatch(s, p + 2);
//        }
//    }

    /**
     First of all, this is one of the most difficulty problems.
     It is hard to think through all different cases.
     The problem should be simplified to handle 2 basic cases:

     the second char of pattern is "*"
     the second char of pattern is not "*"

     For the 1st case, if the first char of pattern is not ".",
     the first char of pattern and string should be the same.
     Then continue to match the remaining part.

     For the 2nd case, if the first char of pattern is "."
         or first char of pattern == the first i char of string,
     continue to match the remaining part.
     */
    public class Solution {
        public boolean isMatch(String s, String p) {

            if (p.length() == 0) {
                return s.length() == 0;
            }

            //p's length 1 is special case
            if (p.length() == 1 || p.charAt(1) != '*') {
                // 不是*的情况
                if (s.length() < 1 || (p.charAt(0) != '.' && s.charAt(0) != p.charAt(0))) {
                    return false;
                }
                return isMatch(s.substring(1), p.substring(1));

            } else {
                // 是*的情况
                int len = s.length();
                int i = -1;
                while (i < len && (i < 0 || p.charAt(0) == '.' || p.charAt(0) == s.charAt(i))) {
                    if (isMatch(s.substring(i + 1), p.substring(2)))
                        return true;
                    i++;
                }
                return false;
            }
        }
    }

}
