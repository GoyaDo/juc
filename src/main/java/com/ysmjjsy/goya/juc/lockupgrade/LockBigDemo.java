package com.ysmjjsy.goya.juc.lockupgrade;

/**
 * @author goya
 * @create 2022-01-07 13:31
 * 锁粗化
 * 假如方法中首尾相接，前后相邻的都是同一个锁对象，那JIT编译器就会把这几个synchronized块合并成一个大块，
 * 加粗加大范围，一次申请锁使用即可，避免次次的申请和释放锁，提升了性能。
 */
public class LockBigDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // 会把下面四个synchronized合成一个
            synchronized (objectLock) {
                System.out.println("---1");
            }
            synchronized (objectLock) {
                System.out.println("---2");
            }
            synchronized (objectLock) {
                System.out.println("---3");
            }
            synchronized (objectLock) {
                System.out.println("---4");
            }
        }, "t1").start();
    }
}
