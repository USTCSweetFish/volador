package com.ayu.growing.service;

import com.ayu.growing.rss.RabbitMqService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private AmqpTemplate couponMQTemplate;

//    @Autowired
//    private RabbitMqConfig rabbitMqConfig;

    @Resource(name = "rabbitTemplate")
    public void setCouponMQTemplate(AmqpTemplate couponMQTemplate) {
        this.couponMQTemplate = couponMQTemplate;
    }


    /**
     * 消息入队
     * @param
     * @return
     */
    @Override
    public boolean produce(Object obj) {
        return false;
    }


    /**
     * 消息出队
     */
    @Override
    public boolean consume(Object obj) {
        return false;
    }
}
