package com.journey.multithreading.amino;

import org.amino.ds.lockfree.LockFreeSet;

/**
 * Set:
 *  LockFreeSet
 *
 * Created by xiaxiangnan on 16/5/20.
 */
public class AminoSet {

    public static void main(String[] args) {
        LockFreeSet<Integer> lockFreeSet = new LockFreeSet<>(); //线程安全无锁set
        lockFreeSet.add(100);
        lockFreeSet.add(200);
        lockFreeSet.add(300);
        lockFreeSet.forEach((Integer i) -> System.out.println(i));
    }
}
