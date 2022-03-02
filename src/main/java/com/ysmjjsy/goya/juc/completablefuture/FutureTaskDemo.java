package com.ysmjjsy.goya.juc.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

/**
 * @author goya
 * @create 2022-01-03 10:34
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 1024;
        });
        new Thread(futureTask, "t1").start();
//        System.out.println("----继续执行");
//        System.out.println(futureTask.get()); // 不见不散！推荐放到最后，因为get()不管计算完成都阻塞等待结果出来再运行
//        System.out.println(futureTask.get(2L, TimeUnit.SECONDS)); //过时不候
        /*
            高并发：克服阻塞，尽量少加锁不加锁
                工作中不要阻塞，高并发，别忘记CAS
                用轮询替代阻塞
         */
        while (true) {
            if (futureTask.isDone()) {
                System.out.println("---result:" + futureTask.get());
                break;
            } else {
                System.out.println("还在计算中");
            }
        }
        /*
            轮询的方式会耗费无谓的CPU资源，而且也不见得能及时得到计算结果
            如果想要异步获取结果，通常都会以轮询的方式去获取结果，尽量不要阻塞
         */
    }
}
