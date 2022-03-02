package com.ysmjjsy.goya.juc.tl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author goya
 * @create 2022-01-06 20:40
 */
public class ThreadLocalDateUtils {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss");

    public static Date sparse(String stringDate) throws ParseException {
        return simpleDateFormat.parse(stringDate);
    }

    // 建议用如下方法
    // ThreadLocal可以确保每个线程都可以得到各自单独的一个SimpleDateFormat的对象，那么自然也就不存在竞争问题
    public static final ThreadLocal<SimpleDateFormat> sdfThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss"));

    public static final Date parseByThreadLocal(String stringDate) throws ParseException {
        return sdfThreadLocal.get().parse(stringDate);
    }

    //阿里手册
    // 用DateTimeFormatter代替SimpleDateFormat
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
    public static String format(LocalDateTime localDateTime){
        return DATE_TIME_FORMATTER.format(localDateTime);
    }
    public static LocalDateTime parse(String dateString){
        return LocalDateTime.parse(dateString,DATE_TIME_FORMATTER);
    }

    public static void main(String[] args) throws ParseException {
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                try {
//                    System.out.println(ThreadLocalDateUtils.parse("2011-11-11 11:11:11"));
                    System.out.println(ThreadLocalDateUtils.parseByThreadLocal("2011-11-11 11:11:11"));
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    sdfThreadLocal.remove();
                }
            }, String.valueOf(i)).start();
        }
    }
}
