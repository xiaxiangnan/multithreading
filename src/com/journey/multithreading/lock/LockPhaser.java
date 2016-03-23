package com.journey.multithreading.lock;

import java.util.concurrent.Phaser;

/**
 * 在功能上面它与CyclicBarrier、CountDownLatch有些重叠，但是它提供了更加灵活、强大的用法。
 * 它把多个线程协作执行的任务划分为多个阶段，编程时需要明确各个阶段的任务，每个阶段都可以有任意个参与者，
 * 线程都可以随时注册并参与到某个阶段。
 * <p>
 * Created by xiaxiangnan on 16/3/16.
 */
public class LockPhaser {

    public static void main(String[] args) throws InterruptedException {
        Task1 task1 = new Task1(new Phaser(5));
        for (int i = 0; i < 5; i++) {
            new Thread(task1).start();
        }


        Thread.sleep(3000);
        System.out.println("---------------- Task1 END ------------------------");


        Phaser phaser2 = new Phaser(1); //相当于CountDownLatch(1)
        Task2 task2 = new Task2(phaser2);
        for (int i = 0; i < 3; i++) {
            new Thread(task2).start();
        }
        //等待3秒
        Thread.sleep(2000);
        phaser2.arrive(); //相当于countDownLatch.countDown()


        Thread.sleep(3000);
        System.out.println("---------------- Task2 END ------------------------");


        /**
         * Phaser内有2个重要状态，分别是phase和party。phase就是阶段，初值为0，当所有的线程执行完本轮任务，同时开始下一轮任务时，
         * 意味着当前阶段已结束，进入到下一阶段，phase的值自动加1。party就是线程，party=3就意味着Phaser对象当前管理着3个线程
         */
        Phaser phaser3 = new Phaser(3) {
            /**
             * 当每一个阶段执行完毕，此方法会被自动调用
             * @param phase the current phase number on entry to this method,
             *         before this phaser is advanced
             * @param registeredParties the current number of registered parties
             * @return 返回true时，意味着Phaser被终止,因此可以巧妙的设置此方法的返回值来终止所有线程
             *
             * 该方法默认return registeredParties == 0,即只执行一次就中断Phaser
             */
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("执行onAdvance方法.....;phase:" + phase + "registeredParties=" + registeredParties);
                return phase == 2;
            }
        };
        Task3 task3 = new Task3(phaser3);
        for (int i = 0; i < 2; i++) {
            new Thread(task3).start();
        }
        while (!phaser3.isTerminated()) {
            System.out.println("主线程一直等待start--------------");
            phaser3.arriveAndAwaitAdvance();    //主线程一直等待
            System.out.println("主线程一直等待end---------------");
        }
        System.out.println("主线程任务已经结束....");


    }

    /**
     * 类似于CyclicBarrier
     */
    static class Task1 implements Runnable {

        private Phaser phaser;

        public Task1(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
            //自己完成等待其他参与者完成，进入阻塞，直到Phaser成功进入下个阶段
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "继续执行任务...");
        }
    }

    /**
     * 类似于CountDownLatch
     */
    static class Task2 implements Runnable {

        private Phaser phaser;

        public Task2(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "提交任务...");
            phaser.awaitAdvance(phaser.getPhase());  //相当于countDownLatch.await()
            System.out.println(Thread.currentThread().getName() + "执行任务...");
        }
    }

    /**
     * 高级用法
     */
    static class Task3 implements Runnable {

        private Phaser phaser;

        public Task3(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行任务...");
                //自己完成等待其他参与者完成，进入阻塞，直到Phaser成功进入下个阶段
                phaser.arriveAndAwaitAdvance();
            } while (!phaser.isTerminated());
        }
    }



}
