package com.ayu.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ShieldDB {
    @Value("${spring.datasource.shield.url}")
    private String url;

    @Value("${spring.datasource.shield.username}")
    private String userName;

    @Value("${spring.datasource.shield.password}")
    private String passWord;

    @Value("${spring.datasource.shield.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.shield.type}")
    private String type;
}
