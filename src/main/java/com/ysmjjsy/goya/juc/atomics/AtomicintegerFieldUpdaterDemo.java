package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author goya
 * @create 2022-01-06 12:37
 */
public class AtomicintegerFieldUpdaterDemo {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    bankAccount.transfer(bankAccount);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }

            }, String.valueOf(i)).start();
        }
        try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + "\t" + "---bankAccount: " + bankAccount.getMoney());
    }
}

class BankAccount {
    String bankName = "ccb";

    // 以一种线程安全的方式操作非线程安全对象内的某些字段

    // 更新的对象属性必须使用public volatile修饰符
    private volatile int money = 0;

    AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public void transfer(BankAccount bankAccount) {
        fieldUpdater.incrementAndGet(bankAccount);
    }

    public int getMoney(){
        return money;
    }
}