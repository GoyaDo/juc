package com.ysmjjsy.goya.juc.threadpool;

import java.util.concurrent.*;

/**
 * @author goya
 * @create 2022-01-08 16:52
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            for (int i = 1; i <= 10; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }

    }
}
