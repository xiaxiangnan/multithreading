package com.journey.multithreading.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件（也称为条件队列 或条件变量）为线程提供了一个含义，以便在某个状态条件现在可能为 true 的另一个线程通知它之前，一直挂起该线程（即让其“等待”）
 * 因为访问此共享状态信息发生在不同的线程中，所以它必须受保护，因此要将某种形式的锁与该条件相关联。
 * 等待提供一个条件的主要属性是：以原子方式 释放相关的锁，并挂起当前线程，就像 Object.wait 做的那样
 * PROJECT: multithreading
 * Created by xiaxiangnan on 16-2-4.
 */
public class LockCondition {

    private int depotSize;     //仓库大小
    private Lock lock;         //独占锁
    private int capacity;       //仓库容量

    private Condition fullCondition;
    private Condition emptyCondition;


    public LockCondition() {
        this.depotSize = 0;
        this.lock = new ReentrantLock();
        this.capacity = 15;
        this.fullCondition = lock.newCondition();
        this.emptyCondition = lock.newCondition();
    }

    /**
     * 在Condition中，用await()替换wait()，用signal()替换 notify()，用signalAll()替换notifyAll()，
     * 对于我们以前使用传统的Object方法，Condition都能够给予实现。
     * <p/>
     * 商品入库
     */
    public void put(int value) {
        lock.lock();
        System.out.println("Producer get lock");
        try {
            int left = value;
            while (left > 0) {
                //库存已满时，“生产者”等待“消费者”消费
                while (depotSize >= capacity) {
                    fullCondition.await(); //与此 Condition 相关的锁以原子方式释放，并且出于线程调度的目的，将禁用当前线程。
                }
                //获取实际入库数量：预计库存（仓库现有库存 + 生产数量） > 仓库容量   ? 仓库容量 - 仓库现有库存     :    生产数量
                //                  depotSize   left   capacity  capacity - depotSize     left
                int inc = depotSize + left > capacity ? capacity - depotSize : left;
                depotSize += inc;
                left -= inc;
                System.out.println(Thread.currentThread().getName() + "----要入库数量: " + value + ";;实际入库数量：" + inc + ";;仓库货物数量：" + depotSize + ";;没有入库数量：" + left);

                //通知消费者可以消费了
                emptyCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Producer unlock");
            lock.unlock();
        }
    }

    /**
     * 商品出库
     */
    public void get(int value) {
        lock.lock();
        System.out.println("Customer get lock");
        try {
            int left = value;
            while (left > 0) {
                //仓库已空，“消费者”等待“生产者”生产货物
                while (depotSize <= 0) {
                    emptyCondition.await();
                }
                //实际消费      仓库库存数量     < 要消费的数量     ?   仓库库存数量     : 要消费的数量
                int dec = depotSize < left ? depotSize : left;
                depotSize -= dec;
                left -= dec;
                System.out.println(Thread.currentThread().getName() + "----要消费的数量：" + value + ";;实际消费的数量: " + dec + ";;仓库现存数量：" + depotSize + ";;有多少件商品没有消费：" + left);

                //通知生产者可以生产了
                fullCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Customer unlock");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockCondition lockCondition = new LockCondition();

        Producer producer = new Producer(lockCondition);
        Customer customer = new Customer(lockCondition);

        producer.produce(10);
        customer.consume(5);
        producer.produce(15);
        customer.consume(10);
        customer.consume(15);
        producer.produce(10);
    }

    public static class Producer {
        private LockCondition lockCondition;

        public Producer(LockCondition lockCondition) {
            this.lockCondition = lockCondition;
        }

        public void produce(final int value) {
            new Thread() {
                public void run() {
                    lockCondition.put(value);
                }
            }.start();
        }
    }

    public static class Customer {
        private LockCondition lockCondition;

        public Customer(LockCondition lockCondition) {
            this.lockCondition = lockCondition;
        }

        public void consume(final int value) {
            new Thread() {
                public void run() {
                    lockCondition.get(value);
                }
            }.start();
        }
    }

}