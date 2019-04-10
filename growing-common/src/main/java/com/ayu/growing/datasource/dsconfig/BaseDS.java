package com.ayu.growing.datasource.dsconfig;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.ayu.growing.datasource.dbconfig.*;
import com.bilibili.growing.datasource.dbconfig.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 单数据库数据源配置
 */
@Configuration
public class BaseDS {
    @Autowired
    private OpenPayDB openPayDB;

    @Autowired
    private ShieldDB shieldDB;

    @Autowired
    private PayDB payDB;

    @Autowired
    private OpenPay2DB openPay2DB;

    @Autowired
    private OpenPaySlaveDB openPaySlaveDB;

    @Autowired
    private OpenPay2SlaveDB openPay2SlaveDB;

    @Autowired
    private PaySlaveDB paySlaveDB;

    @Autowired
    private ShieldSlaveDB shieldSlaveDB;

    @Autowired
    private OpenPayMngDB openPayMngDB;

    @Bean
    @Primary
    public DataSource openPaySource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("openPay");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        bean.setTestQuery("select 1");
        Properties properties = new Properties();
        properties.put("URL", openPayDB.getUrl());
        properties.put("user", openPayDB.getUserName());
        properties.put("password", openPayDB.getPassWord());
        bean.setXaProperties(properties);
        //bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource openPayMngSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("openPayMng");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", openPayMngDB.getUrl());
        properties.put("user", openPayMngDB.getUserName());
        properties.put("password", openPayMngDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource openPaySlaveSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("openPaySlave");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", openPaySlaveDB.getUrl());
        properties.put("user", openPaySlaveDB.getUserName());
        properties.put("password", openPaySlaveDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource openPay2Source() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("openPay2");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", openPay2DB.getUrl());
        properties.put("user", openPay2DB.getUserName());
        properties.put("password", openPay2DB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource openPay2SlaveSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("openPay2Slave");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", openPay2SlaveDB.getUrl());
        properties.put("user", openPay2SlaveDB.getUserName());
        properties.put("password", openPay2SlaveDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource shieldSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("shield");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", shieldDB.getUrl());
        properties.put("user", shieldDB.getUserName());
        properties.put("password", shieldDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource shieldSlaveSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("shieldSlave");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", shieldSlaveDB.getUrl());
        properties.put("user", shieldSlaveDB.getUserName());
        properties.put("password", shieldSlaveDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource paySource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("pay");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", payDB.getUrl());
        properties.put("user", payDB.getUserName());
        properties.put("password", payDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }

    @Bean
    public DataSource paySlaveSource() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setUniqueResourceName("paySlave");
        bean.setXaDataSourceClassName(
                "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setPoolSize(3);
        Properties properties = new Properties();
        properties.put("URL", paySlaveDB.getUrl());
        properties.put("user", paySlaveDB.getUserName());
        properties.put("password", paySlaveDB.getPassWord());
        bean.setXaProperties(properties);
        bean.setTestQuery("select 1");
        return bean;
    }
}
