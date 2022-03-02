package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 * @author goya
 * @create 2022-01-06 14:06
 */
public class AtomicReferenceFieldUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
        /*
            0	---start init
            1	---抢夺失败，已经有线程在修改中
            2	---抢夺失败，已经有线程在修改中
            3	---抢夺失败，已经有线程在修改中
            4	---抢夺失败，已经有线程在修改中
            0	---end init
         */
    }
}

/**
 * 微服务中，多个系统要求饱和调用其中一个系统，先到先得
 */
class MyVar{
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar,Boolean> fieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");

    public void init(MyVar myVar){
        if (fieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE)){
            System.out.println(Thread.currentThread().getName()+"\t"+"---start init");
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---end init");
        }else {
            System.out.println(Thread.currentThread().getName()+"\t"+"---抢夺失败，已经有线程在修改中");
        }
    }
}
