package com.journey.multithreading.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入的互斥锁定Lock，是一种递归无阻塞的同步机制
 * 锁的可重入性：当线程请求一个由其它线程持有锁的对象时，该线程会阻塞，但是当线程请求由自己持有锁的对象时，是否可以成功呢？
 *              答案是可以成功的，成功的保障就是线程锁的“可重入性”
 * PROJECT: multithreading
 * Created by xiaxiangnan on 16-2-3.
 */
public class ThreadReentrantLock {

    /**
     * 默认构造方法，非公平锁
     * 公平性：所有线程均可以公平地获得CPU运行机会
     */
    Lock lock = new ReentrantLock();
    /**
     * true公平锁，false非公平锁
     */
    Lock fairLock = new ReentrantLock(true);

    /**
     * lock()方法会对Lock实例对象进行加锁，因此所有对该对象调用lock()方法的线程都会被阻塞，直到该Lock对象的unlock()方法被调用。
     */
    public void test(){
        lock.lock();
        //do something
        lock.unlock();
    }






}
