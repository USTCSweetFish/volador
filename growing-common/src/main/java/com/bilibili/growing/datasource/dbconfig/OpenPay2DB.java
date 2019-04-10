package com.bilibili.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OpenPay2DB {
    @Value("${spring.datasource.openPay2.url}")
    private String url;

    @Value("${spring.datasource.openPay2.username}")
    private String userName;

    @Value("${spring.datasource.openPay2.password}")
    private String passWord;

    @Value("${spring.datasource.openPay2.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.openPay2.type}")
    private String type;
}
