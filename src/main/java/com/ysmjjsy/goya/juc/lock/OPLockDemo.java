package com.ysmjjsy.goya.juc.lock;

import java.lang.annotation.Retention;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 乐观锁与悲观锁
 * @author goya
 * @create 2022-01-04 15:24
 */
public class OPLockDemo {

    // 悲观锁的调用方式
    public synchronized void m1(){
        //加锁后的业务逻辑...
    }
    //保证多个线程使用的是同一个lock对象的前提下
    ReentrantLock lock = new ReentrantLock(true);
    public void m2(){
        lock.lock();
        try {
            // 操作同步资源
        }finally {
            lock.unlock();
        }
    }

    public void m3(){
        //乐观锁的调用方式
        //保证多个线程使用的是同一个AtomicInteger
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }



}
