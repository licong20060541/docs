package com.algorithm.gfind;

import java.util.Vector;

/**
 * Sort Colors
 * 时间复杂度O(m+n) 空间复杂度O(1)
 *
 * Given an array with n objects colored red, white or blue, sort them so that
 * objects of the same color are adjacent, with the colors in the order red, white and blue.
 Here, we will use the integers 0, 1, and 2 to represent the color red, white,and blue
 respectively. Note: You are not suppose to use the library’s sort function for this problem.
 Follow up:
 A rather straight forward solution is a two-pass algorithm using counting sort.
 First, iterate the array counting number of 0’s, 1’s, and 2’s, then overwrite array with
 total number of 0’s, then 1’s and followed by 2’s.
 Could you come up with an one-pass algorithm using only constant space?

 看完算法，题意是 0 1 2分别代表 红绿蓝， 排序使得 000000 1111 22222

 1 计数排序，需要扫描两遍，不符合题目
 2 设置red和blue的index，两边往中间走O(n) O(1)
 3 利用快速排序思想，第一次将数组按0分割，第二次按1分割，排序完毕，可推广到n中颜色

 *
 */
public class Algorithm007 {

    class Solution1 {

        void sortColors(Vector<Integer> A) {
            int counts[] =  new int[3]; // 记录每个颜色出现的次数

            for (int i = 0; i < A.size(); i++)
                counts[A.get(i)]++;

            for (int i = 0, index = 0; i < 3; i++)
                for (int j = 0; j < counts[i]; j++)
                    A.set(index++, j);
        }
    }

//    class Solution2 { // 双指针，上述法2
//
//        void sortColors(int[] A) {
//
//            int red = 0;
//            int blue = A.length - 1;
//
//            for (int i = 0; i < blue + 1;) {
//                if (A[i] == 0)
//                    swap(A[i++], A[red++]);
//                else if (A[i] == 2)
//                    swap(A[i], A[blue--]);
//                else
//                    i++;
//            }
//        }
//    }

    // 利用快排思想
//    class Solution3 {
//        public:
//        void sortColors(vector<int>& nums) {
//            partition(partition(nums.begin(), nums.end(), bind1st(equal_to<int>(), 0)),
//        } };


//    class Solution4 { // 重新实现partition

//        void sortColors(vector<int>& nums) {
//            partition(partition(nums.begin(), nums.end(), bind1st(equal_to<int>(), 0)),
//            nums.end(), bind1st(equal_to<int>(), 1));
//        }

//        template<typename ForwardIterator, typename UnaryPredicate>
//        ForwardIterator partition(ForwardIterator first, ForwardIterator last,
//                                  UnaryPredicate pred) {
//            auto pos = first;
//            for (; first != last; ++first)
//                if (pred(*first))
//            swap(*first, *pos++);
//            return pos; }
//    };


}
