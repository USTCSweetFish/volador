package com.bilibili.growing.constant;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 时间常量（多次使用，所以统一入口）
 */
public class TimeConstant {
    private static final String timeFormat1 = "yyyy-MM-dd HH:00:00";
    public static final DateTimeFormatter payMngPayHourDateVersionFormatter = DateTimeFormat.forPattern(timeFormat1);

    private static final String timeFormat2 = "yyyy-MM-dd HH:mm:00";
    public static final DateTimeFormatter payMngPayMinuteDateVersionFormatter = DateTimeFormat.forPattern(timeFormat2);

    private static final String timeFormat3 = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter commonFormatter = DateTimeFormat.forPattern(timeFormat3);

    private static final String timeFormat4 = "yyyy-MM-dd 00:00:00";
    public static final DateTimeFormatter payMngDayFormatter = DateTimeFormat.forPattern(timeFormat4);

    private static final String timeFormat5 = "yyyy-MM-00 00:00:00";
    public static final DateTimeFormatter payMngMonthFormatter = DateTimeFormat.forPattern(timeFormat5);
}
