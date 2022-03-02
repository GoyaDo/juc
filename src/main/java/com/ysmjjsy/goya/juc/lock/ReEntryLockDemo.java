package com.ysmjjsy.goya.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author goya
 * @create 2022-01-04 17:59
 */
public class ReEntryLockDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
//        new ReEntryLockDemo().m1();
        Lock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t"+"---外层");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t"+"---中层");
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName()+"\t"+"---内层");
                    }finally {
//                        lock.unlock(); // 注释掉依然可以执行
                    }
                }finally {
                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("---2");
            }finally {
                lock.unlock();
            }
        },"t2").start(); // 这里因为获取不到锁所以出现死锁问题，因为上面锁没释放干净，要保证加锁几次解锁几次

    }

    /**
     * 同步方法
     */
    public synchronized void m1(){
        System.out.println("---m1");
        m2();
    }
    public synchronized void m2(){
        System.out.println("---m2");
        m3();
    }
    public synchronized void m3(){
        System.out.println("---m3");
    }

    /**
     * 同步代码块
     */
    private static void syncBlock() {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("外层"); // 外层
                synchronized (objectLock) {
                    System.out.println("中层"); // 中层
                    synchronized (objectLock) {
                        System.out.println("内层"); // 内层
                    }
                }
            }
        }, "a").start();
    }
}
