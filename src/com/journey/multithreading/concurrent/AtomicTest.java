package com.journey.multithreading.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子性
 * Created by xiaxiangnan on 16/4/5.
 */
public class AtomicTest {

    /**
     * 原子性布尔 AtomicBoolean: 原子方式进行读和写的布尔值
     */
    public static void atomicBoolean() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean value = atomicBoolean.get();
        System.out.println("atomicBoolean get: " + value);

        atomicBoolean.set(false);
        //返回AtomicBoolean当前的值，并将为AtomicBoolean设置一个新值
        boolean oldValue = atomicBoolean.getAndSet(true);
        System.out.println("atomicBoolean getAndSet oldValue: " + oldValue + ", newValue: " + atomicBoolean.get());

        boolean expectedValue = true;
        boolean newValue = false;
        //AtomicBoolean的当前值与一个期望值进行比较，如果当前值等于期望值的话，将会对AtomicBoolean设定一个新值,否则不替换
        boolean wasNewValueSet1 = atomicBoolean.compareAndSet(expectedValue, newValue);
        System.out.println("atomicBoolean compareAndSet1 wasNewValueSet: " + wasNewValueSet1 + ", newValue: " + atomicBoolean.get());
        boolean wasNewValueSet2 = atomicBoolean.compareAndSet(expectedValue, newValue);
        System.out.println("atomicBoolean compareAndSet2 wasNewValueSet: " + wasNewValueSet2 + ", newValue: " + atomicBoolean.get());
    }

    /**
     * 原子性整型 AtomicInteger: 原子性读和写操作的 int 变量
     */
    public static void atomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.set(123);
        //当前值与一个期望值进行比较，如果当前值等于期望值的话，将会对设定一个新值,否则不替换
        boolean wasNewValueSet = atomicInteger.compareAndSet(123, 234);
        System.out.println("atomicInteger compareAndSet wasNewValueSet: " + wasNewValueSet + ", newValue: " + atomicInteger.get());

        System.out.println("atomicInteger getAndAdd: " + atomicInteger.getAndAdd(10));
        System.out.println("atomicInteger addAndGet: " + atomicInteger.addAndGet(10));
        //每次只将AtomicInteger的值加1
        System.out.println("atomicInteger getAndIncrement: " + atomicInteger.getAndIncrement());
        //-1
        System.out.println("atomicInteger decrementAndGet: " + atomicInteger.decrementAndGet());
    }

    /**
     * 原子性引用型 AtomicReference: 原子性读和写的对象引用变量
     * 原子性的意思是多个想要改变同一个AtomicReference的线程不会导致AtomicReference处于不一致的状态
     */
    public static void atomicReference() {
        String initialReference = "the initially referenced string";
        AtomicReference<String> atomicReference = new AtomicReference<>(initialReference);
        atomicReference.set("set referenced");
        System.out.println("atomicReference value: " + atomicReference.get());

        String newReference = "new value referenced";
        //比较两个引用是一样的(并非 equals() 的相等，而是 == 的一样)
        boolean exchanged = atomicReference.compareAndSet(initialReference, newReference);
        System.out.println("atomicReference compareAndSet exchanged: " + exchanged + ", newValue: " + atomicReference.get());
        exchanged = atomicReference.compareAndSet("set referenced", newReference);
        System.out.println("atomicReference compareAndSet exchanged: " + exchanged + ", newValue: " + atomicReference.get());

    }


    public static void main(String[] args) {
        atomicBoolean();
        atomicInteger();
        atomicReference();
    }

}
