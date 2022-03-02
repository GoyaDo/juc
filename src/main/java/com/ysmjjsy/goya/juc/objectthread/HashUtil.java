package com.ysmjjsy.goya.juc.objectthread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author goya
 * @create 2022-01-07 09:52
 */
public class HashUtil {
    public static void countHash(Object object) throws NoSuchFieldException, IllegalAccessException {
        // 手动计算HashCode
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        long hashCode = 0;
        //！！！注意，由于是小端存储，我们将从后往前计算
        for (long index = 7; index > 0; index--) {
            //取Mark Word中的每一个Byte进行计算
            hashCode |= (unsafe.getByte(object, index) & 0xFF) << ((index - 1) * 8);
        }
        String code = Long.toHexString(hashCode);
        System.out.println("HashUtil calculated hashcode ---0x" + code);
    }
}
