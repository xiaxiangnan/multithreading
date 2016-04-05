package com.journey.multithreading.queue;

import java.util.concurrent.*;

/**
 * 阻塞队列 BlockingQueue(接口)
 * BlockingQueue 通常用于一个线程生产对象，而另外一个线程消费这些对象的场景:
 * 一个线程将会持续生产新对象并将其插入到队列之中，直到队列达到它所能容纳的临界点。也就是说，它是有限的。
 * 如果该阻塞队列到达了其临界点，负责生产的线程将会在往里边插入新对象时发生阻塞。它会一直处于阻塞之中，
 * 直到负责消费的线程从队列中拿走一个对象。负责消费的线程将会一直从该阻塞队列中拿出对象。
 * 如果消费线程尝试去从一个空的队列中提取对象的话，这个消费线程将会处于阻塞之中，直到一个生产线程把一个对象丢进队列。
 * <p>
 * 实现类:
 * ArrayBlockingQueue
 * DelayQueue
 * LinkedBlockingQueue
 * PriorityBlockingQueue
 * SynchronousQueue
 * <p>
 * Created by xiaxiangnan on 16/3/23.
 */
public class BlockingQueueTest {

    /**
     * 数组阻塞队列ArrayBlockingQueue: 是一个有界的阻塞队列，其内部实现是将对象放到一个数组里。
     * 以 FIFO(先进先出)的顺序对元素进行存储,头元素在所有元素之中是放入时间最久的那个，而尾元素则是最短的那个。
     */
    public static void arrayBlockingQueue() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        queue.put("1");
        String value = queue.take();
        System.out.println(value);
    }

    /**
     * 链阻塞队列LinkedBlockingQueue: 内部以一个链式结构(链接节点)对其元素进行存储。可以选择一个上限,
     * 如果没有定义上限，将使用 Integer.MAX_VALUE 作为上限.
     * 以FIFO(先进先出)的顺序对元素进行存储。头元素在所有元素之中是放入时间最久的那个，而尾元素则是最短的那个。
     */
    public static void linkedBlockingQueue() throws InterruptedException {
        BlockingQueue<String> unbounded = new LinkedBlockingQueue<>();
        BlockingQueue<String> bounded = new LinkedBlockingQueue<>(1024);
        bounded.put("1");
        unbounded.put("2");
        System.out.println(bounded.take() + "," + unbounded.take());
    }


    /**
     * 延迟队列DelayQueue: 对元素(实现Delayed接口)进行持有直到一个特定的延迟到期
     * DelayQueue将会在每个元素的getDelay()方法返回的值的时间段之后才释放掉该元素。
     * 如果返回的是0或者负值，延迟将被认为过期，该元素将会在DelayQueue的下一次take被调用的时候被释放掉。
     */
    public static void delayQueue() throws InterruptedException {
        BlockingQueue<Delayed> queue = new DelayQueue<>();
        Delayed element = new Delayed() {

            //到期时间为当前时间+2s
            final long submitTime = TimeUnit.MILLISECONDS.convert(2000, TimeUnit.MILLISECONDS) + System.currentTimeMillis();

            @Override
            public long getDelay(TimeUnit unit) {
                //2s后延迟到期
                return unit.convert(submitTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }

            @Override
            public int compareTo(Delayed o) {
                return 0;
            }
        };
        queue.put(element);
        System.out.println("get delayQueue start");
        Delayed delayed = queue.take();
        System.out.println("get delayQueue end: " + delayed);
    }

    /**
     * 具有优先级的阻塞队列PriorityBlockingQueue: 是一个无界的并发队列.
     * 它使用了和类 java.util.PriorityQueue 一样的排序规则。你无法向这个队列中插入 null 值。
     * 所有插入到 PriorityBlockingQueue 的元素必须实现 java.lang.Comparable 接口。
     * 因此该队列中元素的排序就取决于你自己的 Comparable 实现
     */
    public static void priorityBlockingQueue() throws InterruptedException {
        BlockingQueue<String> queue   = new PriorityBlockingQueue<>();
        //String implements java.lang.Comparable
        queue.put("priorityBlockingQueue");
        System.out.println(queue.take());
    }

    /**
     * 同步队列SynchronousQueue: 内部并没有数据缓存空间,因为数据元素只有当你试着取走的时候才可能存在
     * 可以这样来理解：生产者和消费者互相等待对方，握手，然后一起离开。
     */
    public static void synchronousQueue() throws InterruptedException {
        BlockingQueue<String> queue   = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        queue.put("synchronousQueue");

    }


    public static void main(String[] args) throws Exception {
        arrayBlockingQueue();
        linkedBlockingQueue();
        delayQueue();
        priorityBlockingQueue();
        synchronousQueue();
    }


}
