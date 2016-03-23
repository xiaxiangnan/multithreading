package com.journey.multithreading.lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

/**
 * ReadWriteLock， 维护了一对相关的锁，一个用于只读操作，另一个用于写入操作,
 * 一个资源能够被多个读线程访问，或者被一个写线程访问，但是不能同时存在读写线程.
 * Created by xiaxiangnan on 16/2/22.
 */
public class ThreadReadWriteLock {

    static Logger logger = Logger.getLogger("");

    private String value = "default";

    public void read() {
        try {
            logger.info(Thread.currentThread().getName() + " prepare read");
            lock.readLock().lock();
            logger.info(Thread.currentThread().getName() + " read value is :" + value);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }

    }

    public void write() {
        try {
            logger.info(Thread.currentThread().getName() + " prepare write");
            lock.writeLock().lock();
            value = String.valueOf(ThreadLocalRandom.current().nextInt());
            logger.info(Thread.currentThread().getName() + " write value " + value);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock(false);

    public static void main(String[] args) {
        ThreadReadWriteLock readWriteLock = new ThreadReadWriteLock();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                readWriteLock.write();
                readWriteLock.read();
            }).start();
        }

        new Thread(() -> {
            readWriteLock.read();
        }).start();
    }

}
