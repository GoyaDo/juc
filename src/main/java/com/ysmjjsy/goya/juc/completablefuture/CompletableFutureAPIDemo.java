package com.ysmjjsy.goya.juc.completablefuture;

import java.util.concurrent.*;

/**
 * @author goya
 * @create 2022-01-03 14:15
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

    }

    private static void thenComposeDemo() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 2;
        });
        System.out.println(future.thenCompose((i) -> {
            return CompletableFuture.supplyAsync(() -> {
                return i+2; // 3
            });
        }).join());
    }

    private static void thenCombineDemo() {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return 20;
        }), (r1, r2) -> {
            return r1 + r2; // 30
        }).join()); // 30
    }

    private static void applyToEitherDemo() {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            return 1;
        }).applyToEither(CompletableFuture.supplyAsync(() -> { // 一个执行结束另外一个还继续执行
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }), r -> {
            return r;
        }).join());

        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private static void thenAcceptDemo() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenAccept(r -> {
            System.out.println(r); // 7
        });

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join()); // null

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept((resultA) -> {
        }).join()); // null

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + "resultB").join()); // resultAresultB

        threadPoolExecutor.shutdown();
    }

    private static void handleDemo() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 1;
        }).handle((f, e) -> {
            System.out.println("---1");
            int i = 10 / 0;
            return f + 2;
        }).handle((f, e) -> {
            System.out.println("---2");
            return f + 3;
        }).handle((f, e) -> {
            System.out.println("---3 ");
            return f + 4;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("---result: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join());

        threadPoolExecutor.shutdown();
    }

    /**
     * thenApply
     */
    private static void thenApplyDemo() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            System.out.println("---1");
            return f + 2;
        }).thenApply(f -> {
            System.out.println("---2");
            return f + 3;
        }).thenApply(f -> {
            System.out.println("---3 ");
            return f + 4;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("---result: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join());

        threadPoolExecutor.shutdown();
    }

    /**
     * 获得结果和触发计算
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void m1() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 11;
        }, threadPoolExecutor);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(future3.complete(-44) + "\t" + future3.get());

        threadPoolExecutor.shutdown();
    }
}
