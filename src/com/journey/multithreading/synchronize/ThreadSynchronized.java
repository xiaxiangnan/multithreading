package com.journey.multithreading.synchronize;

/**
 * 线程的同步与锁
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-4.
 */
public class ThreadSynchronized {

    private int x;

    /**
     *  锁为类的对象
     */
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


    private String lock;
    /**
     * String对象lock，线程的lock实际上指向的是堆内存中的同一个区域，所以对象锁是唯一且共享的
     */
    public int getX3() {
        synchronized (lock) {
            return x;
        }
    }

    /**
     * 锁为类Class
     */
    public synchronized static void classLock() {
        System.out.println("classLock start");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("classLock end");
    }

    /**
     * 锁为类的对象
     */
    public synchronized void objLock() {
        System.out.println("objLock start");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("objLock end");
    }

    public static void main(String[] args) {
        final ThreadSynchronized ts = new ThreadSynchronized();
        new Thread(){
            @Override
            public void run() {
                ts.objLock();
                ts.classLock();
            }
        }.start();

        ThreadSynchronized.classLock();
    }

}

