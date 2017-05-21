package com.algorithm.gfind;

/**
 * Search for a Range
 * 时间复杂度O(m+n) 空间复杂度O(1)
 *
 * Given a sorted array of integers, find the starting and ending position of a given target value.
 * Your algorithm’s runtime complexity must be in the order of O(log n).
 If the target is not found in the array, return [-1, -1].
 For example, Given [5, 7, 7, 8, 8, 10] and target value 8, return [3, 4].

 找到两个边界

 排好序，二分法

 O(log n) --- O(1)

 */
public class Algorithm008 {
//
//
//    public int[] searchRange(int[] A, int target) {
//        2         int [] res = {-1,-1};
//        3         if(A == null || A.length == 0)
//            4             return res;
//        5
//        6         //first iteration, find target wherever it is
//        7         int low = 0;
//        8         int high = A.length-1;
//        9         int pos = 0;
//        10         while(low <= high){
//            11             int mid = (low + high)/2;
//            12             pos = mid;
//            13             if(A[mid] > target)
//                14                 high = mid - 1;
//            15             else if(A[mid] < target)
//                16                 low = mid + 1;
//            17             else{
//                18                 res[0] = pos;
//                19                 res[1] = pos;
//                20                 break;
//                21             }
//            22         }
//        23
//        24         if(A[pos] != target)
//            25             return res;
//        26
//        27         //second iteration, find the right boundary of this target
//        28         int newlow = pos;
//        29         int newhigh = A.length-1;
//        30         while(newlow <= newhigh){
//            31             int newmid = (newlow+newhigh)/2;
//            32             if(A[newmid] == target)
//                33                 newlow = newmid + 1;
//            34             else
//            35                 newhigh = newmid - 1;
//            36         }
//        37         res[1] = newhigh;
//        38
//        39         //third iteration, find the left boundary of this target
//        40         newlow = 0;
//        41         newhigh = pos;
//        42         while(newlow <= newhigh){
//            43             int newmid = (newlow+newhigh)/2;
//            44             if(A[newmid] == target)
//                45                 newhigh = newmid - 1;
//            46             else
//            47                 newlow = newmid + 1;
//            48         }
//        49         res[0] = newlow;
//        50
//        51         return res;
//        52     }

//    vector<int> searchRange (vector<int>& nums, int target) {
//        auto lower = lower_bound(nums.begin(), nums.end(), target);
//        auto uppper = upper_bound(lower, nums.end(), target);
//        if (lower == nums.end() || *lower != target)
//        return vector<int> { -1, -1 };
//        else
//        return vector<int> {distance(nums.begin(), lower), distance(nums.begin(), prev(upp
//        }
//        template<typename ForwardIterator, typename T>
//                ForwardIterator lower_bound (ForwardIterator first,
//                ForwardIterator last, T value) {
//            while (first != last) {
//                auto mid = next(first, distance(first, last) / 2);
//                if (value > *mid)   first = ++mid;
//                else                last = mid;
//            }
//            return first;
//        }
//        template<typename ForwardIterator, typename T>
//                ForwardIterator upper_bound (ForwardIterator first,
//                ForwardIterator last, T value) {
//            while (first != last) {
//                auto mid = next(first, distance (first, last) / 2);
//                if (value >= *mid) first = ++mid; //   lower_bound
//                else                 last = mid;
//            }
//            return first;
//        }
//    }

}
