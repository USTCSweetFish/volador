package com.ayu.growing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
public class GrowingApplication {
	private static final Logger logger = LoggerFactory.getLogger(GrowingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GrowingApplication.class, args);
	}

}
