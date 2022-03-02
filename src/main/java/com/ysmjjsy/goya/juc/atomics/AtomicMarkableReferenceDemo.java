package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author goya
 * @create 2022-01-06 12:13
 */
public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100,false);

    public static void main(String[] args) {
        new Thread(()->{
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"---默认修改标识：" +marked);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicMarkableReference.compareAndSet(100,101,marked,!marked);
        },"t1").start();
        new Thread(()->{
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"---默认修改标识：" +marked);
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = atomicMarkableReference.compareAndSet(100, 522, marked, !marked);
            System.out.println(Thread.currentThread().getName()+"\t"+"---操作是否成功：" +b);
            System.out.println(Thread.currentThread().getName()+"\t"+atomicMarkableReference.getReference());
            System.out.println(Thread.currentThread().getName()+"\t"+atomicMarkableReference.isMarked());
        },"t2").start();

        /*
            t1	---默认修改标识：false
            t2	---默认修改标识：false
            t2	---操作是否成功：false
            t2	101
            t2	true
         */

        /*
            适合做双端检索
            if (!atomicMarkableReference.isMarked()){
            //准备写回内存
            if(!atomicMarkableReference.isMarked()){

                }
            }
         */
    }
}
