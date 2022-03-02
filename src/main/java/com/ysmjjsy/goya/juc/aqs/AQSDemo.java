package com.ysmjjsy.goya.juc.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author goya
 * @create 2022-01-07 15:58
 */
public class AQSDemo {
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        reentrantLock.lock();
        reentrantLock.unlock();
    }
}
