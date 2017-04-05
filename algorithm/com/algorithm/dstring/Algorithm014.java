package com.algorithm.dstring;

import java.util.Stack;

/**
 * Simplify Path
 * 时间复杂度O(n) 空间复杂度O(n)
 * <p>
 * 这道题的要求是简化一个Unix风格下的文件的绝对路径。
 * <p>
 * 字符串处理，由于".."是返回上级目录（如果是根目录则不处理），因此可以考虑用栈记录路径名，以便于处理。需要注意几个细节：
 * <p>
 * 重复连续出现的'/'，只按1个处理，即跳过重复连续出现的'/'；
 * 如果路径名是"."，则不处理；
 * 如果路径名是".."，则需要弹栈，如果栈为空，则不做处理；
 * 如果路径名为其他字符串，入栈。
 * <p>
 * 最后，再逐个取出栈中元素（即已保存的路径名），用'/'分隔并连接起来，不过要注意顺序呦。
 * <p>
 * 时间复杂度：O(n)
 * <p>
 * 空间复杂度：O(n)
 */
public class Algorithm014 {

    public static void main(String args[]) {
        System.out.println("" + new Solution().simplifyPath("/a/./b/../../c/"));
    }

    static class Solution {

        String simplifyPath(String path) {

            Stack<String> ss = new Stack<>(); // 记录路径名
            for (int i = 0; i < path.length(); ) {
                // 跳过斜线'/'
                while (i < path.length() && '/' == path.charAt(i))
                    ++i;
                // 记录路径名
                String s = "";
                while (i < path.length() && path.charAt(i) != '/')
                    s += path.charAt(i++);
                // 如果是".."则需要弹栈，否则入栈
                if ("..".equals(s) && !ss.empty())
                    ss.pop();
                else if (!s.equals("") && !s.equals(".") && !s.equals(".."))
                    ss.push(s);
            }
            // 如果栈为空，说明为根目录，只有斜线'/'
            if (ss.empty())
                return "/";
            // 逐个连接栈里的路径名
            String s = "";
            while (!ss.empty()) {
                s = "/" + ss.pop() + s;
            }
            return s;
        }
    }
}
