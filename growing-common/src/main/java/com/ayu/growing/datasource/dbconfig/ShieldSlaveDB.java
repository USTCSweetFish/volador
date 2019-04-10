package com.ayu.growing.datasource.dbconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:config/dbslave.properties")
public class ShieldSlaveDB {
    @Value("${spring.datasource.shield.slave.url}")
    private String url;

    @Value("${spring.datasource.shield.slave.username}")
    private String userName;

    @Value("${spring.datasource.shield.slave.password}")
    private String passWord;

    @Value("${spring.datasource.shield.slave.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.shield.slave.type}")
    private String type;
}
