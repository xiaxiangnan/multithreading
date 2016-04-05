package com.journey.multithreading.concurrent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * java.util.concurrent.ExecutorService 接口表示一个异步执行机制，使我们能够在后台执行任务。
 * 因此一个ExecutorService很类似于一个线程池。实际上，ExecutorService实现就是一个线程池实现。
 * <p>
 * 实现类：
 * ThreadPoolExecutor
 * ScheduledThreadPoolExecutor
 * <p>
 * Created by xiaxiangnan on 16/4/1.
 */
public class ExecutorServiceTest {

    public static void easyDemo() throws Exception {
        //工厂方法创建一个 ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //1.execute 没有办法得知被执行的 Runnable 的执行结果,如果有需要的话你得使用一个Callable
        executorService.execute(() -> System.out.println("easyDemo execute"));

        //2.submit(Runnable) 返回Future对象可以用来检查 Runnable 是否已经执行完毕
        Future future = executorService.submit(() -> System.out.print("easyDemo Runnable submit: "));
        //get()如有必要，等待计算完成，然后获取其结果
        System.out.println(future.get());  //returns null if the task has finished correctly.
        // submit(Callable)  结果可以通过 submit(Callable) 方法返回的 Future 对象进行获取
        Future future1 = executorService.submit(() -> {
            System.out.print("easyDemo Callable submit: ");
            return "call return";
        });
        System.out.println(future1.get());

        //3. invokeAny 要求一组Callable,一个任务执行结束(或者抛了一个异常)，其他Callable将被取消.
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(() -> "task1");
        callables.add(() -> "task2");
        callables.add(() -> "task3");
        //返回其中一个 Callable 对象的结果
        String result = executorService.invokeAny(callables);
        System.out.println("easyDemo invokeAny: " + result);

        //4. invokeAll 要求一组Callable, 返回一组对应的Future对象
        //注: 一个任务可能会由于一个异常而结束，因此它可能没有 "成功"。无法通过一个Future对象来告知我们是两种结束中的哪一种。
        Set<Callable<String>> callables1 = new HashSet<Callable<String>>();
        callables1.add(() -> "task1");
        callables1.add(() -> "task2");
        callables1.add(() -> "task3");
        List<Future<String>> futures = executorService.invokeAll(callables1);
        System.out.print("easyDemo invokeAll: ");
        futures.forEach((Future fur) -> {
            try {
                System.out.print(fur.get() + ", ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println();

        //ExecutorService并不会立即关闭，但它将不再接受新的任务，一旦所有线程都完成了当前任务的时候，ExecutorService将会关闭
        executorService.shutdown();
        //shutdownNow(): 立即关闭ExecutorService,立即尝试停止所有执行中的任务，并忽略掉那些已提交但尚未开始处理的任务。
        //               无法担保执行任务的正确执行。可能它们被停止了，也可能已经执行结束。
    }

    /**
     * 线程池执行者: threadPoolExecutor
     * 池中线程的数量由以下变量决定：corePoolSize,maximumPoolSize
     * 除非你确实需要显式为ThreadPoolExecutor定义所有参数，使用Executors类中的工厂方法之一会更加方便
     */
    public static void threadPoolExecutor() {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5000;
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(() -> System.out.println("threadPoolExecutor execute"));
        threadPoolExecutor.shutdown();
    }

    /**
     * 定时执行者服务: ScheduledExecutorService, 实现类:ScheduledThreadPoolExecutor
     * 它能够将任务延后执行，或者间隔固定时间多次执行。
     * 任务由一个工作者线程异步执行，而不是由提交任务给ScheduledExecutorService的那个线程执行
     */
    public static void scheduledExecutorService() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(() -> {
                    System.out.println("scheduledExecutorService Executed!");
                    return "Called!";
                },
                5, //将在5秒钟之后被执行
                TimeUnit.SECONDS);
        System.out.println("scheduledExecutorService result: " + scheduledFuture.get());

        /**
         * 该任务将会在3s之后得到执行，然后每个2s时间之后重复执行
         * 如果给定任务的执行抛出了异常，该任务将不再执行。如果没有任何异常的话，这个任务将会持续循环执行到ScheduledExecutorService被关闭。
         * 如果一个任务占用了比计划的时间间隔更长的时候，下一次执行将在当前执行结束执行才开始。计划任务在同一时间不会有多个线程同时执行
         */
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("scheduleAtFixedRate"), 3, 2, TimeUnit.SECONDS);

        System.out.println("sleep 10s");
        Thread.sleep(10000);
        scheduledExecutorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        easyDemo();
        threadPoolExecutor();
        scheduledExecutorService();
    }

}
