package com.bilibili.growing.datasource.dbconfig;

/**
 * 定义数据源名称，数据源可由1到多个数据库组成
 */
public class DSName {

    //open_pay主从库
    public static final String openPay = "openPay";
    public static final String openPaySalve = "openPaySalve";

    //open_pay2主从库
    public static final String openPay2 = "openPay2";
    public static final String openPay2Salve = "openPay2Salve";

    //pay主从库
    public static final String pay = "pay";
    public static final String paySalve = "paySalve";

    //shield主从库 线上为open_shield
    public static final String shield = "shield";
    public static final String shieldSalve = "shieldSalve";

    //open_pay open_pay2 分库
    public static final String openPayOpenPay2 = "openPayOpenPay2";
    public static final String openPayOpenPay2Slave = "openPayOpenPay2Slave";

    //open_paymng主库
    public static final String openPayMng = "openPayMng";
    //public static final String openPaySalve="openPaySalve";

    //为了处理批量插入数据，多定义一个数据源；
    public static final String openPayMngWithOutSharding = "openPayMngWithOutSharding";
}
