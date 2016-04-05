package com.journey.multithreading.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 阻塞双端队列 BlockingDeque(接口)-Double Ended Queue,继承自BlockingQueue接口
 * 双端队列是一个你可以从任意一端插入或者抽取元素的队列。
 * 在线程既是一个队列的生产者又是这个队列的消费者的时候可以使用到 BlockingDeque。
 * 在生产者线程需要在队列的两端都可以插入数据,消费者线程需要在队列的两端都可以移除数据,也可以使用 BlockingDeque
 * <p>
 * 实现类:
 * LinkedBlockingDeque
 * <p>
 * Created by xiaxiangnan on 16/3/28.
 */
public class BlockingDequeTest {

    /**
     * 链阻塞双端队列LinkedBlockingDeque
     */
    public static void linkedBlockingDeque() throws InterruptedException {
        BlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("last");
        deque.putFirst("first");
        System.out.println(deque.getFirst());
        System.out.println(deque.getLast());

    }


    public static void main(String[] args) throws InterruptedException {
        linkedBlockingDeque();
    }

}
