package com.algorithm.dstring;

/**
 * Valid number
 * 时间复杂度O(n) 空间复杂度O(n)
 *
 *
 */
public class Algorithm009 {

    public static void main(String args[]) {

    }


    /**
     *  看了下面这个例子，对正则表达式更加理解了些，再看上面这句就好理解了，给了很大帮助：
     ^[+-]?\d*[.]?\d*$
     ^是界定符，表示匹配字符串的开始；
     [+-]，中括号表示其内的内容都是符合要求的匹配，所以这个表示“+”或者"-"；
     \d，[0-9]的简写形式，也就是匹配数字；
     $也是界定符，表示匹配字符串的结束；
     了解以上之后再来看问号(?)和星号(*)：跟在一个模式内容后面的是量词，用来限定模式内容匹配的次数，
     例如我想要匹配最少1个、最多3个数字，例如4、20、123、226这样的，先前已经解释过单个数字可以用[0-9]或者\d表示，
     那么怎么表示最少匹配1次、最多3次呢？
     很简单，就在模式后面加上大括号配合次数表示量词，形式为{下限,上限}，就是这样[0-9]{1,3}或者\d{1,3}。
     类似的，[0-9]{2}就表示只能匹配2个数字（多一个少一个都不行），[0-9]{2,}表示至少要有2个数字最多
     不限（注意大括号内的逗号），[0-9]{,2}表示最多有2个数字（注意大括号的逗号）。
     针对经常用的量词，正则中也有简写，

     {0,1}简写就是问号（要么没有要么只有一次），

     {0,}简写就是星号（随便有没有也不限次数），

     {1,}简写就是加号（至少有一次）
     */
    public boolean isNumber(String s) {
        if(s.trim().length() == 0){
            return false;
        }
        String regex = "[-+]?(\\d+\\.?|\\.\\d+)\\d*(e[-+]?\\d+)?";
        if(s.trim().matches(regex)){
            return true;
        }else{
            return false;
        }
    }


//    判断合法数字，之前好像在哪里看到过这题，
//
//    记得当时还写了好久，反正各种改，
//
//    今天看到了大神的解法(https://github.com/fuwutu/LeetCode/blob/master/Valid%20Number.cpp),
//
//            用有限状态机，非常简洁，不需要复杂的各种判断！
//
//            先枚举一下各种合法的输入情况：
//
//            1.空格+ 数字 +空格
//
//            2.空格+ 点 + 数字 +空格
//
//            3.空格+ 符号 + 数字 +　空格
//
//            4.空格 + 符号 + 点 +　数字　＋空格
//
//            5.空格 + (1, 2, 3, 4） + e +　(1, 2, 3, 4) +空格
//
//    组后合法的字符可以是：
//
//            1.数字
//
//            2.空格
//
//    有限状态机的状态转移过程：
//
//    起始为0：
//
//            　　当输入空格时，状态仍为0，
//
//            　　输入为符号时，状态转为3，3的转换和0是一样的，除了不能再接受符号，故在0的状态的基础上，把接受符号置为-1；
//
//            　　当输入为数字时，状态转为1, 状态1的转换在于无法再接受符号，可以接受空格，数字，点，指数；状态1为合法的结束状态；
//
//            　　当输入为点时，状态转为2，状态2必须再接受数字，接受其他均为非法；
//
//            　　当输入为指数时，非法；
//
//    状态1：
//
//            　　接受数字时仍转为状态1，
//
//            　　接受点时，转为状态4，可以接受空格，数字，指数，状态4为合法的结束状态，
//
//            　　接受指数时，转为状态5，可以接受符号，数字，不能再接受点，因为指数必须为整数，而且必须再接受数字；
//
//    状态2：
//
//            　　接受数字转为状态4；
//
//    状态3：
//
//            　　和0一样，只是不能接受符号；
//
//    状态4：
//
//            　　接受空格，合法接受；
//
//            　　接受数字，仍为状态4；
//
//            　　接受指数，转为状态5，
//
//    状态5：
//
//            　　接受符号，转为状态6，状态6和状态5一样，只是不能再接受符号，
//
//            　　接受数字，转为状态7，状态7只能接受空格或数字；状态7为合法的结束状态；
//
//    状态6：
//
//            　　只能接受数字，转为状态7；
//
//    状态7：
//
//            　　接受空格，转为状态8，状态7为合法的结束状态；
//
//            　　接受数字，仍为状态7；
//
//    状态8：
//
//            　　接受空格，转为状态8，状态8为合法的结束状态；

//     class Solution
//     {
//         public:
//             bool isNumber(const char *s)
//             {
//                     enum InputType
//                    {
//                                     INVALID,    // 0
//                                     SPACE,      // 1
//                                     SIGN,       // 2
//                                     DIGIT,      // 3
//                                     DOT,        // 4
//                                     EXPONENT,   // 5
//                                     NUM_INPUTS  // 6
//                         };
//
//                     int transitionTable[][NUM_INPUTS] =
//                     {
//                                     -1,  0,  3,  1,  2, -1,     // next states for state 0
//                                     -1,  8, -1,  1,  4,  5,     // next states for state 1
//                                     -1, -1, -1,  4, -1, -1,     // next states for state 2
//                                     -1, -1, -1,  1,  2, -1,     // next states for state 3
//                                     -1,  8, -1,  4, -1,  5,     // next states for state 4
//                                     -1, -1,  6,  7, -1, -1,     // next states for state 5
//                                     -1, -1, -1,  7, -1, -1,     // next states for state 6
//                                     -1,  8, -1,  7, -1, -1,     // next states for state 7
//                                     -1,  8, -1, -1, -1, -1,     // next states for state 8
//                                 };
//
//                     int state = 0;
//                     while (*s != '\0')
//                     {
//                             InputType inputType = INVALID;
//                             if (isspace(*s))
//                                 inputType = SPACE;
//                             else if (*s == '+' || *s == '-')
//                                 inputType = SIGN;
//                             else if (isdigit(*s))
//                                 inputType = DIGIT;
//                             else if (*s == '.')
//                                 inputType = DOT;
//                             else if (*s == 'e' || *s == 'E')
//                                 inputType = EXPONENT;
//
//                             state = transitionTable[state][inputType];
//
//                             if (state == -1)
//                                     return false;
//                             else
//                                 ++s;
//                         }
//
//                     return state == 1 || state == 4 || state == 7 || state == 8;
//                 }
//         };




}