package com.ysmjjsy.goya.juc.objectthread;

/*
    <!-- 分析对象在JVM的大小和分布 -->
        <dependency>
            <groupId>org.openjdk.jol</groupId>
            <artifactId>jol-core</artifactId>
            <version>0.9</version>
        </dependency>
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author goya
 * @create 2022-01-07 09:40
 */
public class ObjectHeadDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Object object = new Object();
        // VM的细节详情
        System.out.println(VM.current().details());
        // 所有的对象分配的字节都是8的整数倍
        System.out.println(VM.current().objectAlignment());
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        System.out.println("--------------");
        System.out.println("before hash");
        MarkWord markWord = MarkWord.builder().age(11).price(22.22).sex(true).build();
        //未计算hash之前的对象头
        System.out.println(ClassLayout.parseInstance(markWord).toPrintable());
        //JVM计算的hashcode
        System.out.println("JVM calculated hashcode---------0x" + Integer.toHexString(markWord.hashCode()));
        HashUtil.countHash(markWord);
        System.out.println("after hash");
        //计算hashcode之后的对象头
        System.out.println(ClassLayout.parseInstance(markWord).toPrintable());

        /*
        # Objects are 8 bytes aligned.
        # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
        # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

        8
        java.lang.Object object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
              0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
              4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4        (object header)                           28 0f 00 00 (00101000 00001111 00000000 00000000) (3880)
             12     4        (loss due to the next object alignment)
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        --------------
        before hash
        com.ysmjjsy.goya.juc.objectthread.ObjectHeadDemo$MarkWord object internals:
         OFFSET  SIZE                TYPE DESCRIPTION                               VALUE
              0     4                     (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
              4     4                     (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4                     (object header)                           30 8a 07 00 (00110000 10001010 00000111 00000000) (494128)
             12     1             boolean MarkWord.sex                              true
             13     3                     (alignment/padding gap)
             16     8              double MarkWord.price                            22.22
             24     4   java.lang.Integer MarkWord.age                              11
             28     4                     (loss due to the next object alignment)
        Instance size: 32 bytes
        Space losses: 3 bytes internal + 4 bytes external = 7 bytes total

        JVM calculated hashcode---------0x92514c38
        HashUtil calculated hashcode ---0x0
        after hash
        com.ysmjjsy.goya.juc.objectthread.ObjectHeadDemo$MarkWord object internals:
         OFFSET  SIZE                TYPE DESCRIPTION                               VALUE
              0     4                     (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
              4     4                     (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4                     (object header)                           30 8a 07 00 (00110000 10001010 00000111 00000000) (494128)
             12     1             boolean MarkWord.sex                              true
             13     3                     (alignment/padding gap)
             16     8              double MarkWord.price                            22.22
             24     4   java.lang.Integer MarkWord.age                              11
             28     4                     (loss due to the next object alignment)
        Instance size: 32 bytes
        Space losses: 3 bytes internal + 4 bytes external = 7 bytes total
         */
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class MarkWord {
        private Integer age;
        private boolean sex;
        private double price;
    }
}
