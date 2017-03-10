package com.algorithm.clist;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * LRU Cache
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and set.
 *   get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
 * otherwise return -1.
 *   set(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently used
 * item before inserting a new item.
 * 
 * 时间复杂度O(logn) 空间复杂度O(n)
 */
public class Algorithm014 {

    public static void main(String args[]) {

        ListNode head = new ListNode(-1);
        ListNode tmp = head;
        for (int i = 1; i <= 5; i++) {
            tmp.next = new ListNode(i);
            tmp = tmp.next;
        }
    }


    private static class ListNode {
        int val;
        ListNode next;
        ListNode random;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    private static class CacheNode {
        int key;
        int value;

        public CacheNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 使用一个双向链表和一个hash表
     * ：哈希表保存每个节点的地址，可以基本保证在O(1)时间内查找节点
     * ：双向链表插入删除方便
     * 实现
     * ：链表头部表示访问时间最短，需要保留，尾部表示最近访问越少，必要删除
     * ：访问节点，移动到头部，并更新hash
     * ：插入节点，达到上限则删除
     */
    class LRUCache{

        LinkedList<CacheNode> cacheList = new LinkedList<>();
        HashMap<Integer, CacheNode> cacheMap = new HashMap<>();
        int capacity;

        LRUCache(int capacity) {
            this.capacity = capacity;
        }

        int get(int key) {
            if (!cacheMap.containsKey(key)) {
                return -1;
            }
            CacheNode cacheNode = cacheMap.get(key);
            cacheList.remove(cacheNode);
            cacheList.add(0, cacheNode);
            return cacheNode.value;
        }

        void set(int key, int value) {
            if (!cacheMap.containsKey(key)) {
                if (cacheList.size() == capacity) {
                    cacheMap.remove(cacheList.removeLast().key);
                }
                CacheNode node = new CacheNode(key, value);
                cacheList.add(0, node);
                cacheMap.put(key, node);
            } else {
                cacheMap.get(key).value = value;
                CacheNode cacheNode = cacheMap.get(key);
                cacheList.remove(cacheNode);
                cacheList.add(0, cacheNode);
            }
        }

    }

}
