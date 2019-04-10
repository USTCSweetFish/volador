package com.ayu.growing.datasource;

public class DataSourceContextHolder {

    public static final String DEFAULT_DS = "master";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //设置数据源名称
    public static void setDS(String dsType) {
        contextHolder.set(dsType);
    }

    //获取数据源名称
    public static String getDS() {
        return contextHolder.get();
    }

    //删除数据源名
    public static void ClearDS() {
        contextHolder.remove();
    }
}
