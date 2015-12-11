package com.journey.multithreading.create;

/**
 * 实现Runnable接口的多线程
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-2.
 */
public class RunnableNewThread implements Runnable {

    private String name;

    public RunnableNewThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + i);
        }
    }

    public static void main(String[] args) {
        RunnableNewThread ds1 = new RunnableNewThread("张三");
        RunnableNewThread ds2 = new RunnableNewThread("李四");
        //通过Thread类调用
        Thread t1 = new Thread(ds1);
        Thread t2 = new Thread(ds2);

        t1.start();
        t2.start();
    }
}