package com.bilibili.growing;

import com.bilibili.growing.entity.JdEntity;

import com.bilibili.growing.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

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
