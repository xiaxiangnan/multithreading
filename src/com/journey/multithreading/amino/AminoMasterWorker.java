package com.journey.multithreading.amino;

import org.amino.pattern.internal.*;

import java.util.List;
import java.util.Vector;

/**
 * master-worker模式: Master进程，负责接收和分配任务；Worker进程，负责处理子任务
 * 1. 静态, 任务开始时不允许添加子任务
 * 2. 动态, 任务开始时允许添加子任务
 * Created by xiaxiangnan on 16/5/20.
 */
public class AminoMasterWorker {

    //静态模式工作逻辑-返回数字的三次方
    public class Pow3 implements Doable<Integer, Integer> {
        @Override
        public Integer run(Integer integer) {
            return integer * integer * integer;
        }
    }

    //动态模式工作逻辑-返回数字的三次方
    public class Pow3Dyn implements DynamicWorker<Integer, Integer> {
        @Override
        public Integer run(Integer integer, WorkQueue<Integer> workQueue) {
            return integer * integer * integer;
        }
    }

    /**
     * 创建并调度静态任务
     */
    public void execute() {
        MasterWorker<Integer, Integer> masterWorker = MasterWorkerFactory.newStatic(new Pow3());
        List<MasterWorker.ResultKey> resultKeyList = new Vector<>();
        for (int i = 0; i < 10; i++) {
            resultKeyList.add(masterWorker.submit(i)); //调度任务,key用于存储结果
        }
        masterWorker.execute();
        int rs = 0;
        if (masterWorker.waitForCompletion()) {
            while (resultKeyList.size() > 0) {
                MasterWorker.ResultKey key = resultKeyList.remove(0);
                Integer i = masterWorker.result(key); //根据key取得一个任务结果
                if (i != null) {
                    rs += i;
                }
            }
        }
        System.out.println("execute result: " + rs);
    }

    /**
     * 创建并调度动态任务
     */
    public void dynExecute() {
        MasterWorker<Integer, Integer> masterWorker = MasterWorkerFactory.newDynamic(new Pow3Dyn());
        List<MasterWorker.ResultKey> resultKeyList = new Vector<>();
        for (int i = 0; i < 9; i++) {
            resultKeyList.add(masterWorker.submit(i)); //调度任务,key用于存储结果
        }
        masterWorker.execute();
        resultKeyList.add(masterWorker.submit(9)); //动态可以在调度开始后,继续添加任务
        int rs = 0;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        while (resultKeyList.size() > 0) {
            MasterWorker.ResultKey key = resultKeyList.remove(0);
            Integer i = masterWorker.result(key); //根据key取得一个任务结果
            if (i != null) {
                rs += i;
            }
        }
        System.out.println("dynExecute result: " + rs);
        masterWorker.shutdown();//动态需要手动停
    }


    public static void main(String[] args) {
        new AminoMasterWorker().execute();
        new AminoMasterWorker().dynExecute();
    }


}
