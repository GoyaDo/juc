package com.ysmjjsy.goya.juc.jmm;

/**
 * 静态内部类方式实现单例模式的安全
 * @author goya
 * @create 2022-01-05 18:41
 */
public class SingletonDemo {

    private SingletonDemo() {
    }

    private static class SingletonDemoHandler {
        private static SingletonDemo instance = new SingletonDemo();
    }

    public static SingletonDemo getInstance() {
        return SingletonDemoHandler.instance;
    }
}

