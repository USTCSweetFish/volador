package com.ayu.growing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    private final int QUERY_SCHEDULE_COREPOOLSIZE=100;
    private final int QUERY_SCHEDULE_MAXPOOLSIZE = 250;
    private final int QUERY_SCHEDULE_QUEUECAPACITY = 50;
    /*
     *查询补偿逻辑调度任务异步线程
     */
    @Bean("queryScheduleTaskExecutor")
    public ThreadPoolTaskExecutor queryScheduleTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(QUERY_SCHEDULE_COREPOOLSIZE);
        executor.setMaxPoolSize(QUERY_SCHEDULE_MAXPOOLSIZE);
        executor.setQueueCapacity(QUERY_SCHEDULE_QUEUECAPACITY);
        executor.setThreadNamePrefix("query_schedule_asyncThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置线程池策略
        executor.initialize();
        return executor;
    }
}
