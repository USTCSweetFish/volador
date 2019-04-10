package com.ayu.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:config/dbslave.properties")
public class PaySlaveDB {
    @Value("${spring.datasource.pay.slave.url}")
    private String url;

    @Value("${spring.datasource.pay.slave.username}")
    private String userName;

    @Value("${spring.datasource.pay.slave.password}")
    private String passWord;

    @Value("${spring.datasource.pay.slave.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.pay.slave.type}")
    private String type;
}
