package com.bilibili.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PayDB {
    @Value("${spring.datasource.pay.url}")
    private String url;

    @Value("${spring.datasource.pay.username}")
    private String userName;

    @Value("${spring.datasource.pay.password}")
    private String passWord;

    @Value("${spring.datasource.pay.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.pay.type}")
    private String type;
}
