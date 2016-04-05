package com.journey.multithreading.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * ConcurrentMap(接口): 并发处理的java.util.Map
 * <p>
 * 实现类：
 * ConcurrentHashMap
 * ConcurrentNavigableMap
 * <p>
 * Created by xiaxiangnan on 16/3/28.
 */
public class ConcurrentMapTest {

    /**
     * 和java.util.HashTable类很相似,但ConcurrentHashMap能够提供比HashTable更好的并发性能
     */
    public static void concurrentHashMap() {
        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("key", "value");
        System.out.println(concurrentMap.get("key"));
    }

    /**
     * 一个支持并发访问的java.util.NavigableMap，它还能让它的子map具备并发访问的能力。
     * 所谓的"子map"指的是诸如headMap()，subMap()，tailMap()之类的方法返回的map
     */
    public static void concurrentNavigableMap() {
        ConcurrentNavigableMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        /**
         * 如果你对原始map里的元素做了改动，这些改动将影响到子map中的元素(map集合持有的其实只是对象的引用)
         */
        //headMap(T toKey)方法返回一个包含了小于给定toKey的key的子map
        ConcurrentNavigableMap<String, String> headMap = map.headMap("3");
        //tailMap(T fromKey) 方法返回一个包含了不小于给定 fromKey 的 key 的子 map
        ConcurrentNavigableMap tailMap = map.tailMap("2");
        //subMap() 方法返回原始 map 中，键介于 from(包含) 和 to (不包含) 之间的子 map
        ConcurrentNavigableMap subMap = map.subMap("1", "3");

        /**
         * ConcurrentNavigableMap 接口还有其他一些方法可供使用,例如:
         * descendingKeySet()
         * descendingMap()
         * navigableKeySet()
         * ......
         */

        System.out.println("headMap: " + headMap);
        System.out.println("tailMap: " + tailMap);
        System.out.println("subMap: " + subMap);
        System.out.println("-------------------------------");
        map.remove("1");
        map.put("4", "four");
        System.out.println("headMap: " + headMap);
        System.out.println("tailMap: " + tailMap);
        System.out.println("subMap: " + subMap);
    }

    public static void main(String[] args) {
        concurrentHashMap();
        concurrentNavigableMap();
    }
}
