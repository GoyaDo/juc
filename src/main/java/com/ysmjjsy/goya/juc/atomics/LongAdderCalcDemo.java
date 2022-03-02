package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 50个线程，每个线程100w次，总点赞数出来
 *
 * @author goya
 * @create 2022-01-06 14:43
 */
public class LongAdderCalcDemo {
    public static final int SIZE_THREAD = 50;
    public static final int _1w = 10000;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch5 = new CountDownLatch(SIZE_THREAD);
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1w; j++) {
                        clickNumber.add_synchronized();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + " 毫秒" + "\t add_Synchronized" + "\t" + clickNumber.number);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1w; j++) {
                        clickNumber.add_AtomicInteger();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + " 毫秒" + "\t add_AtomicInteger" + "\t" + clickNumber.atomicInteger.get());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1w; j++) {
                        clickNumber.add_AtomicLong();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + " 毫秒" + "\t add_AtomicLong" + "\t" + clickNumber.atomicLong.get());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1w; j++) {
                        clickNumber.add_LongAdder();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + " 毫秒" + "\t add_LongAdder" + "\t" + clickNumber.longAdder.longValue());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1w; j++) {
                        clickNumber.add_LongAccumulator();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch5.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch5.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + " 毫秒" + "\t add_LongAccumulator" + "\t" + clickNumber.longAccumulator.longValue());
    }
}

/**
 * 五大统计方法
 *
 * ---costTime: 1277 毫秒	 add_Synchronized	50000000
 * ---costTime: 1996 毫秒	 add_AtomicInteger	50000000
 * ---costTime: 1984 毫秒	 add_AtomicLong	50000000
 * ---costTime: 130 毫秒	 add_LongAdder	50000000
 * ---costTime: 102 毫秒	 add_LongAccumulator	50000000
 */
class ClickNumber {

    int number = 0;

    public synchronized void add_synchronized() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void add_AtomicInteger() {
        atomicInteger.incrementAndGet();
    }

    AtomicLong atomicLong = new AtomicLong();

    public void add_AtomicLong() {
        atomicLong.incrementAndGet();
    }

    LongAdder longAdder = new LongAdder();

    public void add_LongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void add_LongAccumulator() {
        longAccumulator.accumulate(1);
    }

}