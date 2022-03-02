package com.ysmjjsy.goya.juc.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author goya
 * @create 2022-01-08 10:42
 */
public class ArrayListDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayListDemo arrayListDemo = new ArrayListDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (arrayListDemo) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "arrayListDemo");
                }
            }, "t1").start();
        }
        synchronized (ArrayListDemo.class) {
            System.out.println(Thread.currentThread().getName() + "\t" + "main run");
        }
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                synchronized (ArrayListDemo.class) {
//                    System.out.println(Thread.currentThread().getName() + "\t" + "ArrayListDemo.class");
//                }
//            }, "t2").start();
//        }


    }

    private static void copyOnWriteArrayListDemo() {
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
//                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(arrayList.toString());
            }, String.valueOf(i)).start();
        }
//        for (String s :
//                arrayList) {
//            System.out.println(s.toString());
//        }
    }

    private static void collectionsDemo() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
//                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list.toString());
            }, String.valueOf(i)).start();
        }
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (String s :
//                list) {
//            System.out.println(s.toString());
//        }
    }

    private static void vectorDemo() {
        Vector<String> vector = new Vector();
        for (int i = 1; i < 3; i++) {
            int finalI1 = i;
            new Thread(() -> {
//                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                vector.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(vector.toString());
            }, String.valueOf(i)).start();
        }
//        Enumeration en = vector.elements();//返回的是实现类的对象
//        while (en.hasMoreElements()) {
//            String s = (String) en.nextElement();
//            System.out.println(s);
//        }
//        for(int x = 0; x < vector.size(); x++){
//            String s = (String)vector.elementAt(x);
//            System.out.println(s);
//        }
//
//        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
//        for(int x = 0; x < vector.size(); x++){
//            String s = (String)vector.elementAt(x);
//            System.out.println(s);
//        }
    }

    private static void arrayListDemo() {
        ArrayList<String> arrayList = new ArrayList<String>(); //Exception in thread "main" java.util.ConcurrentModificationException
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(arrayList.toString());
            }, String.valueOf(i)).start();
        }
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (String s :
//                arrayList) {
//            System.out.println(s.toString());
//        }
    }
}
