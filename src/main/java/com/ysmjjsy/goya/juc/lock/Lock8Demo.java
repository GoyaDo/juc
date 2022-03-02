package com.ysmjjsy.goya.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * 谈谈你对多线程的理解？
 * 线程操作资源类
 * 1、标准访问有ab两个线程，请问先打印邮件还是短信                     -- Email
 * 2、sendEmail方法暂停3秒钟，请问先打印邮件还是短信                  -- Email
 *      1-2：一个对象里面如果有多个synchronized方法，某一时刻内，只有一个线程去调用其中的一个synchronized方法了。
 *          其他线程都只能等待，换句话说，某一时刻内，只能有唯一的一个线程去访问这些synchronized方法。
 *          锁的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其他synchronized方法。
 * 3、新增一个普通的hello方法，请问先打印邮件还是hello                 -- hello
 * 4、有两部手机，请问先打印邮件还是短信                               -- SMS
 *      3-4：加个普通方法后发现和同步锁无关。
 *          换成两个对象后，不是同一把锁了，情况立刻变化
 * 5、两个静态同步方法，同一部手机，请问先打印邮件还是短信                 -- Email
 * 6、两个静态同步方法，两部手机，请问先打印邮件还是短信                   -- Email
 *      5-6：都换成静态同步方法后，情况有变化
 *          三种synchronized锁的内容有一些差别：
 *          对于普通同步方法，锁的是当前实例对象，通常指this，具体的一部部手机，所有的普通方法用的都是同一把锁-实例对象本身
 *          对于静态同步方法，锁的是当前类的Class对象，如Phone.class唯一的一个模板，锁的是类，是类锁。
 *          对于同步方法块，锁的是synchronized括号内的对象。
 * 7、一个静态同步方法，一个普通同步方法，同一部手机，请问先打印邮件还是短信   -- SMS
 * 8、一个静态同步方法，一个普通同步方法，两部手机，请问先打印邮件还是短信     -- SMS
 *      7-8：当一个线程试图访问同步代码块时他首先必须得到锁，退出或抛出异常时必须释放锁
 *          所有的普通同步方法用的都是同一把锁——实例对象本身，就是new出来的具体实例对象本身，本类this
 *          也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁
 *          所有的静态同步方法用的也是同一把锁——类对象本身，就是我们说过的唯一模板Class
 *          具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的
 *          但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 *
 * @author goya
 * @create 2022-01-04 15:32
 */
public class Lock8Demo {
    public static void main(String[] args) { // 一切程序的入口，主线程
        phone phone = new phone(); // 资源类1
        phone phone2 = new phone(); // 资源类2
        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
//            phone.sendSMS();
//            phone.hello();
            phone2.sendSMS();
        }, "b").start();
    }
}

// 资源类
class phone {
    public static synchronized void sendEmail() {
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("---sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("---sendSMS");
    }

    public void hello() {
        System.out.println("---hello");
    }
}