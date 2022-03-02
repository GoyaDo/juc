package com.ysmjjsy.goya.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * @author goya
 * @create 2022-01-05 16:35
 */
public class VolatileSeeDemo {

//    static volatile boolean flag = true; // 运行停止
    static  boolean flag = true; // 无法停止 volatile保证可见性

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
            while (flag){
                new Integer(308);
            }
            System.out.println("t1 over");
        },"t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(()->{
            flag = false;
        },"t2").start();
    }
}
