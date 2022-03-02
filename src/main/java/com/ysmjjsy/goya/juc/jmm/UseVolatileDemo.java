package com.ysmjjsy.goya.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * 使用：作为一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或任务结束
 * 理由：状态标志并不依赖于程序内任何其他状态，且通常只有一种状态转换。
 * 例子：判断业务是否结束。
 * @author goya
 * @create 2022-01-05 18:14
 */
public class UseVolatileDemo {

    private volatile static boolean flag = true;

    public static void main(String[] args) {

    }

    /**
     * 使用：当读远多于写，结合使用内部锁和volatile变量来减少同步的开销
     * 理由：利用volatile保证读取操作的可见性；利用synchronized保证复合操作的原子性
     * */
    public class Counter{
        private volatile int value;
        public int getValue(){
            return value;
        }
        public synchronized int increment(){
            return value++;// 理由synchronized保证复合操作的原子性
        }
    }





    private static void m1() {
        new Thread(() -> {
            while (flag){
                // do something...
            }
        }, "t1").start();
        try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() -> {
            flag = false;
        }, "t2").start();
    }
}
