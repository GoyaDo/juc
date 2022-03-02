package com.ysmjjsy.goya.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author goya
 * @create 2022-01-05 19:31
 */
public class prepareCASDemo {

    volatile int number = 0;
    //读取
    public int getNumber(){
        return number;
    }

    //写入加锁保证原子性
    public synchronized void setNumber(){
        number++;
    }

    //-----------------

    AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger(){
        return atomicInteger.get();
    }

    public void setAtomicInteger(){
        atomicInteger.getAndIncrement();
    }
}
