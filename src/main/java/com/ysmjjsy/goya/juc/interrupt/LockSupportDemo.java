package com.ysmjjsy.goya.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author goya
 * @create 2022-01-04 22:00
 */
public class LockSupportDemo {

    static Object objectLokck = new Object();

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            LockSupport.park();
//            LockSupport.park();这里两个park
            System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---发出通知");
            // 可以提前通知也会被唤醒
            LockSupport.unpark(t1);
//            LockSupport.unpark(t1); 这里只有一个unpark，这个不起作用，上线累加是1，所以1没有被释放
            // 当要阻塞两次或多次，可以创建多个线程去unpark。new Thread3
        }, "t2");
        t2.start();
        /*
            t1	---come in
            t2	---发出通知
            t1	---被唤醒
         */
    }

    private static void lockAwaitSignal() {
        new Thread(()->{
            lock.lock(); // 也需要包裹在lock和unlock里
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock(); // 也需要包裹在lock和unlock里
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "---发出通知");
            }finally {
                lock.unlock();
            }
        },"t2").start();
        /*
            t1	---come in
            t2	---发出通知
            t1	---被唤醒
         */
    }

    private static void syncWaitNotify() {
        new Thread(() -> {
            synchronized (objectLokck) {
                System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
                try {
                    objectLokck.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
            }
        }, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (objectLokck) {
                objectLokck.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "---发出通知");
            }
        }, "t2").start();
    }

    // wait、notify、synchronized三个必须同时出现，不然会出错
    // wait和notify顺序不能错
    private static void m1() {
        new Thread(() -> {
            synchronized (objectLokck) {
                System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
                try {
                    objectLokck.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
            }
        }, "t1").start();
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() -> {
            synchronized (objectLokck) {
                objectLokck.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "---发出通知");
            }
        }, "t2").start();

        /*
            结果：
                t1	---come in
                t2	---发出通知
                t1	---被唤醒
         */
    }
}
