package com.bilibili.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OpenPayDB {

    @Value("${spring.datasource.openPay.url}")
    private String url;

    @Value("${spring.datasource.openPay.username}")
    private String userName;

    @Value("${spring.datasource.openPay.password}")
    private String passWord;

    @Value("${spring.datasource.openPay.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.openPay.type}")
    private String type;

}
