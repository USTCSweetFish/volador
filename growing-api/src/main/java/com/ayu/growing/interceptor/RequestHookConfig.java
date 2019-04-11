package com.ayu.growing.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:
 * @Date: 上午10:26 2017/7/19
 */

@Configuration
public class RequestHookConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/paymng/check/health", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**", "/paymng/login/check");
        super.addInterceptors(registry);
    }
}
