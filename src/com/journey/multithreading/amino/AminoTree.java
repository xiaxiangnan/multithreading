package com.journey.multithreading.amino;

import org.amino.mcas.LockFreeBSTree;

/**
 * tree:
 * LockFreeBSTree: 无锁线程安全的二叉搜索树
 * <p>
 * Created by xiaxiangnan on 16/5/20.
 */
public class AminoTree {

    public static void main(String[] args) {
        LockFreeBSTree<String, String> lockFreeBSTree = new LockFreeBSTree<>();
        for (int i = 0; i < 5; i++) {
            lockFreeBSTree.update(String.valueOf(i + 1), "value" + (i + 1));
        }
        System.out.println(lockFreeBSTree.find("2"));
    }
}
