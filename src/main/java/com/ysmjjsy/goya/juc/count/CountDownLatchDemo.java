package com.ysmjjsy.goya.juc.count;

import java.util.concurrent.CountDownLatch;

/**
 * @author goya
 * @create 2022-01-08 13:46
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国,被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"\t***秦国一统天下");
    }
}

