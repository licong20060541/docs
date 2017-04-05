package com.algorithm.dstring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Anagrams -- LeetCode
 * 时间复杂度O(n) 空间复杂度O(n)
 *
 * 回文构词： 找出一组，她们中的单词名称及个数相同，即2个a，3个d等等
 *
 */
public class Algorithm013 {

    public static void main(String args[]) {

    }

    public ArrayList<String> anagrams(String[] strs) {
        ArrayList<String> res = new ArrayList<>();
        if(strs == null || strs.length == 0)
            return res;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for(int i=0;i<strs.length;i++)
        {
            char[] charArr = strs[i].toCharArray();
            Arrays.sort(charArr);
            String str = new String(charArr);
            if(map.containsKey(str))
            {
                map.get(str).add(strs[i]);
            }
            else
            {
                ArrayList<String> list = new ArrayList<String>();
                list.add(strs[i]);
                map.put(str,list);
            }
        }
        Iterator iter = map.values().iterator();
        while(iter.hasNext())
        {
            ArrayList<String> item = (ArrayList<String>)iter.next();
            if(item.size()>1)
                res.addAll(item);
        }
        return res;
    }


}
