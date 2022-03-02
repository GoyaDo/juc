package com.ysmjjsy.goya.juc.lockupgrade;

import org.openjdk.jol.info.ClassLayout;

/**
 * 无锁
 * @author goya
 * @create 2022-01-07 11:13
 */
public class MyObject {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        /*
            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
                  4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0) // 前两行对象头信息需要倒着看
                  8     4        (object header)                           28 0f 00 00 (00101000 00001111 00000000 00000000) (3880)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
        /*
            1163157884
            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           01 7c 61 54 (00000001 01111100 01100001 01010100) (1415674881)
                  4     4        (object header)                           45 00 00 00 (01000101 00000000 00000000 00000000) (69)
                  8     4        (object header)                           28 0f 00 00 (00101000 00001111 00000000 00000000) (3880)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
    }
}
