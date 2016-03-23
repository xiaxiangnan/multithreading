package com.journey.multithreading.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待.
 * <p>
 * CountDownLatch内部通过“共享锁”实现。在创建CountDownLatch时，需要传递一个int类型的count参数，
 * 该count参数为“锁状态”的初始值，该值表示着该“共享锁”可以同时被多少线程获取。当某个线程调用await方法时，
 * 首先判断锁的状态是否处于可获取状态（其条件就是count==0?），如果共享锁可获取则获取共享锁，
 * 否则一直处于等待直到获取为止。当线程调用countDown方法时，计数器count – 1。
 * 当在创建CountDownLatch时初始化的count参数，必须要有count线程调用countDown方法才会使计数器count等于0，
 * 锁才会释放，前面等待的线程才会继续运行。
 * <p>
 * CountDownlatch与CyclicBarrier有那么点相似，但是他们还是存在一些区别的：
 * 1、CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待
 * 2、CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因此它被称为是循环的barrier
 * <p>
 * Created by xiaxiangnan on 16/3/15.
 */
public class LockCountDownlatch {

    private final CountDownLatch countDown;

    public LockCountDownlatch(int count) {
        countDown = new CountDownLatch(count);
    }

    /**
     * 与会人员到达，调用arrive方法，到达一个CountDownLatch调用countDown方法，锁计数器-1
     */
    public void arrive(String name) {
        System.out.println(name + "到达.....");
        //调用countDown()锁计数器 - 1
        countDown.countDown();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("还有 " + countDown.getCount() + "人没有到达...");
    }

    public void meeting() {
        System.out.println("准备开会，参加会议人员总数为：" + countDown.getCount());
        //调用await()等待所有的与会人员到达
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            ;
        }
        System.out.println("所有人员已经到达，会议开始.....");
    }

    public static void main(String[] args) {
        final LockCountDownlatch countDownlatch = new LockCountDownlatch(3);
        new Thread(() -> countDownlatch.meeting()).start();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> countDownlatch.arrive(Thread.currentThread().getName())).start();
        }
    }

}
