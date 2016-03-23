package com.journey.multithreading.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Exchanger类允许在两个线程之间定义同步点。当两个线程都到达同步点时，他们交换数据结构，
 * 因此第一个线程的数据结构进入到第二个线程中，第二个线程的数据结构进入到第一个线程中。
 * <p>
 * 在Exchanger中，如果一个线程已经到达了exchanger节点时，对于它的伙伴节点的情况有三种：
 * 1、如果它的伙伴节点在该线程到达之间已经调用了exchanger方法，则它会唤醒它的伙伴然后进行数据交换，得到各自数据返回。
 * 2、如果它的伙伴节点还没有到达交换点，则该线程将会被挂起，等待它的伙伴节点到达被唤醒，完成数据交换。
 * 3、如果当前线程被中断了则抛出异常，或者等待超时了，则抛出超时异常。
 * <p>
 * Created by xiaxiangnan on 16/3/21.
 */
public class LockExchanger {

    static class Producer implements Runnable {

        /**
         * 生产者和消费者进行交换的数据结构
         */
        private List<String> buffer;

        /**
         * 同步生产者和消费者的交换对象
         */
        private final Exchanger<List<String>> exchanger;

        Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            int cycle = 1;
            for (int i = 0; i < 3; i++) {
                System.out.println("Producer : Cycle :" + cycle);
                for (int j = 0; j < 3; j++) {
                    String message = "Event " + ((i * 10) + j);
                    System.out.println("Producer : " + message);
                    buffer.add(message);
                }

                //调用exchange()与消费者进行数据交换
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Producer :" + buffer.size());
                cycle++;
            }
        }
    }

    static class Consumer implements Runnable {
        private List<String> buffer;

        private final Exchanger<List<String>> exchanger;

        public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            int cycle = 1;
            for (int i = 0; i < 3; i++) {
                System.out.println("Consumer : Cycle :" + cycle);

                //调用exchange()与生产者进行数据交换
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer :" + buffer.size());
                for (int j = 0; j < 3; j++) {
                    System.out.println("Consumer : " + buffer.get(0));
                    buffer.remove(0);
                }
                cycle++;
            }
        }
    }

    public static void main(String[] args) {
        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();

        Exchanger<List<String>> exchanger = new Exchanger<>();

        Producer producer = new Producer(buffer1, exchanger);
        Consumer consumer = new Consumer(buffer2, exchanger);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);

        thread1.start();
        thread2.start();
    }


}
