package com.journey.multithreading.lock;

import java.util.concurrent.Semaphore;

/**
 * Semaphore一个计数信号量,是一个控制访问多个共享资源的计数器，它本质上是一个“共享锁”。
 * Created by xiaxiangnan on 16/2/22.
 */
public class LocKSemaphore implements Runnable {

    private final Semaphore semaphore;   //声明信号量

    public LocKSemaphore() {
        semaphore = new Semaphore(2);
    }

    public void printJob() {
        try {
            semaphore.acquire();//调用acquire获取信号量
            System.out.println(Thread.currentThread().getName() +
                    "PrintQueue : Printing a job");
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();  //释放信号量
        }
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Going to print a job");
        this.printJob();
        System.out.println(Thread.currentThread().getName() + " the document has bean printed");
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];

        LocKSemaphore printQueue = new LocKSemaphore();

        for(int i = 0 ; i < 10 ; i++){
            threads[i] = new Thread(printQueue, "Thread_" + i);
        }

        for(int i = 0 ; i < 10 ; i++){
            threads[i].start();
        }
    }

}
