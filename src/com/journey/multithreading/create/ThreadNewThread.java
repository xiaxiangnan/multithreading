package com.journey.multithreading.create;

/**
 * 扩展Thread类实现的多线程
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-2.
 */
public class ThreadNewThread extends Thread {

    public ThreadNewThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(this.getName() + " :" + i);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new ThreadNewThread("张三");
        Thread t2 = new ThreadNewThread("李四");
        t1.start();
        t2.start();
    }


}
