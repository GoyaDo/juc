package com.ysmjjsy.goya.juc.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author goya
 * @create 2022-01-05 22:05
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3", 24);
        User li4 = new User("li4", 26);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
    }
}

class User {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}