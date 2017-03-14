package com.algorithm.dstring;

/**
 * Longest Palindromic Substring
 * 问题：给定字符串S，求S中的最长回文子串。
 * 这个有趣的问题常常在面试中出现。为什么呢？因为解决办法有很多种。
 *
 * 首先你要知道回文是什么。回文就是从左右两边读都一样的字符串。例如”aba”是回文，”abc”不是回文。
 *
 * 一个常见的错误
 * 有人很快会想到这样一个方法。这个方法有缺陷，不过很容易修正。
 * 翻转S成为S’。查找S和S’最长公共子串，就是S的最长回文子串。
 * 看起来有道理的样子。用实例检验下。
 * 例如S=”caba”，S’=”abac”。
 * S和S’的最长公共子串是”aba”，确实是S的最长回文子串。
 * 再看个例子。
 * S=”abacdfgdcaba”，S’=”abacdgfdcaba”。
 * S和S’的最长公共子串是”abacd”，不过很明显这不是回文。
 */
public class Algorithm005 {

    public static void main(String args[]) {

    }

    /**
     * 法1： 暴力枚举，以每个元素为中间元素，同时从左右出发，复杂度O(N的平方)--参考法3，会缺失一种情况啊
     */


    /**
     * 法2： 动态规划法O(N2)时间O(N2)空间
     * 我们可以用动态规划(Dynamic Programming即DP)法对暴力穷举法进行改进。
     * 记住，诀窍就是避免重复计算（即重复检测同一子串）。考虑这个例子”ababa”。
     * 如果我们已经检测过”bab”是回文，那么只需判断一下最左右的两个字符（即两个a）是否相同即可判定”ababa”是否回文了。
     *
     * 总结起来就是：
     * 定义二维数组P[i,j]用以表示Si…Sj是回文（true）或不是回文（false）
     * 那么可知P[i,j] = (P[i + 1, j - 1] && Si ==Sj)
     * 初始条件是：P[i, i]=true，P[i, i + 1] = (Si == Si+1)
     * 这个DP法的思路就是，首先可以知道单个字符和两个相邻字符是否回文，然后检测连续三个字符是否回文，然后四个。。。
     * 此算法时间复杂度O(N2)，空间复杂度O(N2)。伪代码如下

     1 string longestPalindromeDP(string s) {
     2   int n = s.length();
     3   int longestBegin = 0;
     4   int maxLen = 1;
     5   bool table[1000][1000] = {false};

     6   for (int i = 0; i < n; i++) {
     7     table[i][i] = true; // 单个元素都为true
     8   }

     9   for (int i = 0; i < n-1; i++) { // 相邻两个元素
     10     if (s[i] == s[i+1]) {
     11       table[i][i+1] = true;
     12       longestBegin = i;
     13       maxLen = 2;
     14     }
     15   }

     16   for (int len = 3; len <= n; len++) { // 依次判断3个元素，4个元素，等等
     17     for (int i = 0; i < n-len+1; i++) {
     18       int j = i+len-1; // 确定如3个元素的范围，然后进行判断
     19       if (s[i] == s[j] && table[i+1][j-1]) {
     20         table[i][j] = true;
     21         longestBegin = i;
     22         maxLen = len;
     23       }
     24     }
     25   }
     26   return s.substr(longestBegin, maxLen);
     27 }
     */


    /**
     * 法3
     * 更简单的算法O(N2)时间O(1)空间---和法1理论一致
     * 下面介绍一个O(N2)时间O(1)空间的算法。
     * 回文的特点，就是中心对称。对于有N个字符的字符串S，只有2N-1个中心。
     * 为何是2N-1？因为两个字符之间的空档也可以是一个中心。例如”abba”的两个b中间就是一个中心。
     * 围绕一个中心检测回文需要O(N)时间，所以总的时间复杂度是O(N2)。

     1 string expandAroundCenter(string s, int c1, int c2) {
     2   int l = c1, r = c2;
     3   int n = s.length();
     4   while (l >= 0 && r <= n-1 && s[l] == s[r]) {
     5     l--;
     6     r++;
     7   }
     8   return s.substr(l+1, r-l-1);
     9 }
     10
     11 string longestPalindromeSimple(string s) {
     12   int n = s.length();
     13   if (n == 0) return "";
     14   string longest = s.substr(0, 1);  // a single char itself is a palindrome
     15   for (int i = 0; i < n-1; i++) {
     16     string p1 = expandAroundCenter(s, i, i);
     17     if (p1.length() > longest.length())
     18       longest = p1;
     19
     20     string p2 = expandAroundCenter(s, i, i+1);
     21     if (p2.length() > longest.length())
     22       longest = p2;
     23   }
     24   return longest;
     25 }
     */


    /**
     * 法4
     * Manacher s Algorithm
     * 算法O(N)时间O(N)空间
     *
     * 首先我们把字符串S改造一下变成T，改造方法是：在S的每个字符之间和S首尾都插入一个"#"。这样做的理由你很快就会知道。
     * 例如，S="abaaba"，那么T="#a#b#a#a#b#a#"。
     *
     * i = 0 1 2 3 4 5 6 7 8 9 A B C
     * T = # a # b # a # a # b # a #
     * P = 0 1 0 3 0 1 6 1 0 3 0 1 0
     */

}
