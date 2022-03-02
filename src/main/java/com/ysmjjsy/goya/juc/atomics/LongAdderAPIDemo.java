package com.ysmjjsy.goya.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author goya
 * @create 2022-01-06 14:31
 */
public class LongAdderAPIDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.longValue()); // 3

        // 0代表从0开始加
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.longValue()); // 6

        LongAccumulator longAccumulator2 = new LongAccumulator((x, y) -> x - y, 100);
        longAccumulator2.accumulate(1);
        longAccumulator2.accumulate(2);
        longAccumulator2.accumulate(3);
        System.out.println(longAccumulator2.longValue()); // 94
    }
}
