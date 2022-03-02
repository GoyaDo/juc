package com.ysmjjsy.goya.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author goya
 * @create 2022-01-05 22:16
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
        // 顺利完成的话返回true，如果失败则让他在里面自旋
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
            //自旋
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "---持有锁成功");
    }

    public void myUnLock() {
        atomicReference.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + "\t" + "---释放锁成功");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnLock();
        }, "t1").start();

        new Thread(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        }, "t2").start();
        /*
            t1	---come in
            t1	---持有锁成功
            t2	---come in
            t1	---释放锁成功
            t2	---持有锁成功
            t2	---释放锁成功
         */
    }
}
