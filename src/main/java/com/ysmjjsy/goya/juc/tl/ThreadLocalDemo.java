package com.ysmjjsy.goya.juc.tl;

/**
 * @author goya
 * @create 2022-01-06 19:34
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
//        MovieTicket movieTicket = new MovieTicket();
//        for (int i = 0; i <= 3; i++) {
//            new Thread(() -> {
//                for (int j = 0; j <= 20; j++) {
//                    movieTicket.saleTicket();
//                }
//            }, String.valueOf(i)).start();
//        }
        House house = new House();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---卖出：" + house.threadLocal.get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                house.threadLocal.remove(); // 必须加remove，不然会造成内存泄漏
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---卖出：" + house.threadLocal.get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                house.threadLocal.remove();
            }
        }, "t2").start();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---卖出：" + house.threadLocal.get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                house.threadLocal.remove();
            }
        }, "t3").start();

        System.out.println(Thread.currentThread().getName() + "\t" + "---卖出：" + house.threadLocal.get());

        /*
            t1	---卖出：3
            t2	---卖出：5
            main	---卖出：0
            t3	---卖出：8
         */

    }
}

class MovieTicket {
    int number = 50;

    public synchronized void saleTicket() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "\t" + (number--));
        } else {
            System.out.println("---卖光了");
        }
    }
}

class House {
    // 初始化赋值
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    // 初始化赋值
//    ThreadLocal<Integer> threadLocal1 = new ThreadLocal<Integer>(){
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    public void saleHouse() {
        Integer value = threadLocal.get();
        ++value;
        threadLocal.set(value);
    }
}