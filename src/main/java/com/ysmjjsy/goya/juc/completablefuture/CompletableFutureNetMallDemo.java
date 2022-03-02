package com.ysmjjsy.goya.juc.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author goya
 * @create 2022-01-03 12:00
 */
public class CompletableFutureNetMallDemo {
    static List<NetMall> list = Arrays.asList(
            // 异步在这里不论增加多少，都是1s
            new NetMall("jd"),
            new NetMall("pdd"),
//            new NetMall("taobao"),
//            new NetMall("dangdang"),
            new NetMall("tabo")
    );

    //同步，step by step
    public static List<String> getPriceByStep(List<NetMall> list, String productName) {
        return list
                .stream().map(netMall -> String.format(productName + " in %s price is %.2f",
                        netMall.getMallName(),
                        netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    //异步
    public static List<String> getPriceByAsync(List<NetMall> list, String productName) {
        return list
                .stream().map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format(productName + " in %s price is %.2f",
                                        netMall.getMallName(),
                                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPriceByStep(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime - startTime) + "毫秒");

        System.out.println();

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByAsync(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("---costTime: " + (endTime2 - startTime2) + "毫秒");

        /*
        <!--- 第一次---!>
            mysql in jd price is 109.48
            mysql in pdd price is 110.81
            mysql in tabo price is 109.52
            ---costTime: 3065毫秒

            mysql in jd price is 109.74
            mysql in pdd price is 109.11
            mysql in tabo price is 109.07
            ---costTime: 1012毫秒

        <!--- 第二次---!>
            mysql in jd price is 110.71
            mysql in pdd price is 109.93
            mysql in taobao price is 110.74
            mysql in dangdang price is 109.49
            mysql in tabo price is 110.53
            ---costTime: 5075毫秒

            mysql in jd price is 109.88
            mysql in pdd price is 109.41
            mysql in taobao price is 110.41
            mysql in dangdang price is 110.63
            mysql in tabo price is 110.85
            ---costTime: 1012毫秒
         */
    }
}

class NetMall {
    private String mallName;

    public NetMall(String mallName) {
        this.mallName = mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMallName() {
        return mallName;
    }

    public double calcPrice(String productName) {
        // 检索需要1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //模拟出价
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}
