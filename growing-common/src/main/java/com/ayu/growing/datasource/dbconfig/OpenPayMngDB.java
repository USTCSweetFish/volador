package com.bilibili.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class OpenPayMngDB {
    @Value("${spring.datasource.mng.url}")
    private String url;

    @Value("${spring.datasource.mng.username}")
    private String userName;

    @Value("${spring.datasource.mng.password}")
    private String passWord;

    @Value("${spring.datasource.mng.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.mng.type}")
    private String type;
}
