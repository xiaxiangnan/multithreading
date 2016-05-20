package com.journey.multithreading.amino;

import org.amino.ds.lockfree.LockFreeList;
import org.amino.ds.lockfree.LockFreeVector;

/**
 * List:
 * 1. LockFreeList:
 *   This is a lock-free implementation intended for highly scalable add,
 *   remove and contains which is thread safe. All method related to index is not implemented.
 *   Add() will add the element to the head of the list which is different with the normal list.
 * 2. LockFreeVector:
 *
 * Created by xiaxiangnan on 16/5/20.
 */
public class AminoList {

    public static void main(String[] args) {
        LockFreeList<String> lockFreeList = new LockFreeList<>(); //线程安全无锁LinkedList
        LockFreeVector<String> lockFreeVector = new LockFreeVector<>(); //线程安全无锁ArrayList

        lockFreeList.add("1");
        lockFreeVector.add("12");

//        System.out.println(lockFreeList.get(0)); //UnsupportedOperationException
        System.out.println(lockFreeList.iterator().next());
        System.out.println(lockFreeVector.get(0));
    }

}
