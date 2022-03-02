package com.ysmjjsy.goya.juc.count;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author goya
 * @create 2022-01-08 14:18
 */
public class CyclicBrrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            final int temInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第: " + temInt + "龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
