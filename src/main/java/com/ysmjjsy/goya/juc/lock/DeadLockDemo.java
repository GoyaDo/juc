package com.ysmjjsy.goya.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author goya
 * @create 2022-01-04 18:23
 */
public class DeadLockDemo {
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lockA){
                System.out.println(Thread.currentThread().getName()+"\t"+"自己持有A锁，期待获得B锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB){
                    System.out.println(Thread.currentThread().getName()+"\t"+"获得B锁成功");
                }
            }
        },"a").start();

        new Thread(()->{
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t"+"自己持有B锁，期待获得A锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockA){
                    System.out.println(Thread.currentThread().getName()+"\t"+"获得A锁成功");
                }
            }
        },"b").start();
    }
    /*
        a	自己持有A锁，期待获得B锁
        b	自己持有B锁，期待获得A锁
        ...
     */
    /*
        证明：jps+jstack/jconsole
            "b" #12 prio=5 os_prio=31 tid=0x000000015a99b000 nid=0x5803 waiting for monitor entry [0x000000017272a000]
               java.lang.Thread.State: BLOCKED (on object monitor)
                    at com.ysmjjsy.goya.juc.lock.DeadLockDemo.lambda$main$1(DeadLockDemo.java:30)
                    - waiting to lock <0x00000007956a7528> (a java.lang.Object)
                    - locked <0x00000007956a7538> (a java.lang.Object)
                    at com.ysmjjsy.goya.juc.lock.DeadLockDemo$$Lambda$2/1096979270.run(Unknown Source)
                    at java.lang.Thread.run(Thread.java:748)

            "a" #11 prio=5 os_prio=31 tid=0x000000015a85b800 nid=0xa903 waiting for monitor entry [0x000000017251e000]
               java.lang.Thread.State: BLOCKED (on object monitor)
                    at com.ysmjjsy.goya.juc.lock.DeadLockDemo.lambda$main$0(DeadLockDemo.java:20)
                    - waiting to lock <0x00000007956a7538> (a java.lang.Object)
                    - locked <0x00000007956a7528> (a java.lang.Object)
                    at com.ysmjjsy.goya.juc.lock.DeadLockDemo$$Lambda$1/2003749087.run(Unknown Source)
                    at java.lang.Thread.run(Thread.java:748)
     */
}
