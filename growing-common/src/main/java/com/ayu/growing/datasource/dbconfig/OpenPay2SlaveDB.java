package com.bilibili.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:config/dbslave.properties")
public class OpenPay2SlaveDB {
    @Value("${spring.datasource.openPay2.slave.url}")
    private String url;

    @Value("${spring.datasource.openPay2.slave.username}")
    private String userName;

    @Value("${spring.datasource.openPay2.slave.password}")
    private String passWord;

    @Value("${spring.datasource.openPay2.slave.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.openPay2.slave.type}")
    private String type;
}
