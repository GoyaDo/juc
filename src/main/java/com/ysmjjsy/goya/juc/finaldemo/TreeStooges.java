package com.ysmjjsy.goya.juc.finaldemo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author goya
 * @create 2022-01-08 19:09
 */
public final class TreeStooges {
    private final Set<String> stooges = new HashSet<>();

    public TreeStooges() {
        stooges.add("a");
        stooges.add("b");
        stooges.add("c");
    }

    public boolean isStooge(String name){
        return stooges.contains(name);
    }
}
