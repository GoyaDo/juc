package com.ysmjjsy.goya.juc.completablefuture;

import java.util.concurrent.*;

/**
 * @author goya
 * @create 2022-01-03 11:11
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                // 暂停几秒钟线程
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).thenApply(f->{ // 再开启异步
            return f+2;
        }).whenComplete((v,e)->{ // 当运算结束
            System.out.println("---result: "+v);
        }).exceptionally(e->{ // 异常信息
            e.printStackTrace();
            return null;
        });

        System.out.println("---main over");

        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭：暂停3秒钟线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        threadPoolExecutor.shutdown();

    }

    public static void m1() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
        });
        System.out.println(future1.get());

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
        }, threadPoolExecutor);
        /*
            如果没有指定Executor的方法，直接使用默认的ForkJoinPool.commonPool()作为它的线程池执行异步代码
            如果指定线程池，则使用我们自定义的或者特别指定的线程池执行异步代码
         */
        System.out.println(future2.get());

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 11;
        });
        System.out.println(future3.get());

        CompletableFuture<Integer> future4 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 12;
        },threadPoolExecutor);
        System.out.println(future4.get());

        threadPoolExecutor.shutdown();
    }
}
