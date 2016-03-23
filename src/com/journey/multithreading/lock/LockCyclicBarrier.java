package com.journey.multithreading.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point).
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待.
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 *
 * 对于失败的同步尝试，CyclicBarrier 使用了一种要么全部要么全不 (all-or-none) 的破坏模式
 * <p>
 * Created by xiaxiangnan on 16/3/14.
 */
public class LockCyclicBarrier {

    private CyclicBarrier barrier;

    public LockCyclicBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public void test() {
        System.out.println(Thread.currentThread().getName() + "达到...");
        try {
            /**
             * 线程等待到'这组线程中'所有线程都执行await才会继续进行。
             */
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行完成...");
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * CyclicBarrier(int parties)
         */
        final LockCyclicBarrier cyclicBarrier = new LockCyclicBarrier(new CyclicBarrier(5));
        for (int i = 0; i < 4; i++) {
            new Thread(() -> cyclicBarrier.test()).start();
        }
        System.out.println("wait 2 second");
        Thread.sleep(2000);
        new Thread(() -> cyclicBarrier.test()).start();

        Thread.sleep(2000);
        System.out.println("-----------------分割线-----------------------");

        /**
         * CyclicBarrier(int parties, Runnable barrierAction)
         * 在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次
         */
        final LockCyclicBarrier cyclicBarrier2 = new LockCyclicBarrier(new CyclicBarrier(5, () -> System.out.println("执行CyclicBarrier中的任务.....")));
        for (int i = 0; i < 5; i++) {
            new Thread(() -> cyclicBarrier2.test()).start();
        }
    }

}
