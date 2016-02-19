package com.journey.multithreading.synchronize;

/**
 * 测试
 *
 * PROJECT: multithreading
 * Created by xiaxiangnan on 15-12-4.
 */
public class Test {


    public synchronized void search() {
        System.out.println("search -----" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("search end -----" + Thread.currentThread().getName());
    }

    public synchronized void insert() {
        System.out.println("insert -----" + Thread.currentThread().getName());
    }



    public static void main(String[] args) {
        final Test t1 = new Test();


        /**
         * 如果一个线程在对象上获得一个锁，就没有任何其他线程可以进入（该对象的）类中的任何一个同步方法。
         */
        new Thread(){
            @Override
            public void run() {
                t1.search();
            }

        }.start();
        new Thread(){
            @Override
            public void run() {
                t1.insert();
            }
        }.start();


    }

}
