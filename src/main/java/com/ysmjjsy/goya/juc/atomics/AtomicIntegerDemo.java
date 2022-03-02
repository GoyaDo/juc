package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author goya
 * @create 2022-01-06 11:37
 */
public class AtomicIntegerDemo {
    public static final int SIZE_ = 50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        // 倒计数50个，每做一次减一个，减到0程序通过
        CountDownLatch countDownLatch = new CountDownLatch(SIZE_); // 阻拦锁
        for (int i = 0; i < SIZE_; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j <= 100000; j++) {
                        myNumber.addPlusPlus();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown(); // 确保让上面的线程执行完，不会被挂起
                }
            },String.valueOf(i)).start();
        }
        countDownLatch.await(); // 让main线程在这里阻塞，当上面的线程执行完了main线程才会执行
        System.out.println(Thread.currentThread().getName()+"\t"+"---result: "+myNumber.atomicInteger.get());
    }

    private static void m1() {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < SIZE_; i++) {
            new Thread(()->{
                for (int j = 0; j <= 100000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        /*
            让睡眠1s，则计算结束main可以拿到最终值
         */
        System.out.println(Thread.currentThread().getName()+"\t"+"---result: "+myNumber.atomicInteger.get());
        /*
            main	---result: 3797737
            这里没有到50*100000是因为计算还没有完成就被main线程拿来输出
         */
    }
}

class MyNumber{
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus(){
        atomicInteger.incrementAndGet();
    }
}