package com.ysmjjsy.goya.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author goya
 * @create 2022-01-04 19:15
 */
public class InterruptDemo {

    static volatile boolean isStop = false;

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        // 程序这里根本停不下来
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("---isInterrupted() = true,程序结束.");
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { // 线程的中断标志位为false，需要再次调用interrupt（）设置true
                    Thread.currentThread().interrupt(); // 重新调用一次，解决停不下来的问题
                    e.printStackTrace();
                }
                System.out.println("---hello Interrupted");
            }
        }, "t1");
        t1.start();
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }

    private static void m4() {
        // 中断为true后，并不是立刻stop程序
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("---i: "+i);
            }
            System.out.println("t1.interrupt()调用之后02: "+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1.interrupt()调用之前,t1线程的中断标识默认值: "+t1.isInterrupted());
        try { TimeUnit.MILLISECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        t1.interrupt();
        //活跃状态，t1线程还在执行中
        System.out.println("t1.interrupt()调用之后01: "+t1.isInterrupted());
        try { TimeUnit.MILLISECONDS.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        //非活跃状态，t1线程不在执行中，已经结束执行了
        System.out.println("t1.interrupt()调用之后03: "+t1.isInterrupted());
    }

    private static void m3() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("---isInterrupted() = true,程序结束.");
                    break;
                }
                System.out.println("---hello Interrupt");
            }
        }, "t1");
        t1.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            t1.interrupt(); // 修改t1线程的中断标志位为true。
        }, "t2").start();
    }

    //通过AtomicBoolean
    private static void m2() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println("---atomicBoolean.get() = true,程序结束.");
                    break;
                }
                System.out.println("---hello atomicBoolean");
            }
        }, "t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    // 通过volatile实现
    private static void m1() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println("---isStop = true,程序结束.");
                    break;
                }
                System.out.println("---hello isStop");
            }
        }, "t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
