package com.journey.multithreading.status;

/**
 * 线程的状态的转换
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-4.
 */
public class ThreadStatus {


    /**
     * 睡眠 :静态方法强制当前正在执行的线程休眠（暂停执行）在苏醒之前不会返回到可运行状态。当睡眠时间到期，则返回到可运行状态。
     *
     * 1、线程睡眠是帮助所有线程获得运行机会的最好方法。

     * 2、线程睡眠到期自动苏醒，并返回到可运行状态，不是运行状态。sleep()中指定的时间是线程不会运行的最短时间。因此，sleep()方法不能保证该线程睡眠到期后就开始执行。

     * 3、sleep()是静态方法，只能控制当前正在运行的线程。
     *
     */
    public void sleep() {
        try {
            Thread.sleep(123);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 让步: 暂停当前正在执行的线程对象，并执行其他线程
     *   线程总是存在优先级，优先级范围在1~10之间。JVM线程调度程序是基于优先级的抢先调度机制。在大多数情况下，当前运行的线程优先级将大于或等于线程池中任何线程的优先级。但这仅仅是大多数情况。
     *
     * yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中
     *
     *
     *结论：yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。
     */
    public void yield() {
        Thread.yield();
    }


    /**
     * Thread的非静态方法join()让一个线程B“加入”到另外一个线程A的尾部。在A执行完毕之前，B不能工作
     *
     */
    public void join() throws InterruptedException {
        Thread t = new Thread();
        t.start();
        t.join();
    }

}
