package com.ysmjjsy.goya.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author goya
 * @create 2022-01-08 15:05
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
        /*
            AAA	 put 1
            BBB	1
            AAA	 put 2
            BBB	2
            AAA	 put 3
         */

    }

    private static void m4() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
        blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
        blockingQueue.offer("a", 2L, TimeUnit.SECONDS); // 只阻塞两秒
    }

    private static void m3() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        System.out.println("---");
//        blockingQueue.put("x"); // 阻塞 当队列里被消费一个才可以添加

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
//        blockingQueue.take(); // 阻塞 当队列里生产了一个才可以移除
    }

    private static void m2() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a")); // true
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d")); // false

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll()); // a
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll()); // null
    }

    private static void m1() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a")); // true
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d")); // Exception in thread "main" java.lang.IllegalStateException: Queue full

        System.out.println(blockingQueue.element()); // 队首元素

        System.out.println(blockingQueue.remove()); // a
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove()); // Exception in thread "main" java.util.NoSuchElementException
    }
}
