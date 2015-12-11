package com.journey.multithreading.synchronize;

/**
 * 线程的同步与锁
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-4.
 */
public class ThreadSynchronized {

    private int x;

    public synchronized int getX() {
        return x++;
    }

    /**
     * 效果和getX是完全一样的。
     */
    public int getX2() {
        synchronized (this) {
            return x;
        }
    }



}

