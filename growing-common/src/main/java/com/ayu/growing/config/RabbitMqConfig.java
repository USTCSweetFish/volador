package com.bilibili.growing.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@Data
//@Configuration
@PropertySource("classpath:config/rabbitmq.properties")
public class RabbitMqConfig {

    /**
     * mq主机名
     */
    @Value("${mq.coupon.host}")
    private String hostName;

    /**
     * 端口号
     */
    @Value("${mq.coupon.port}")
    private Integer port;

    /**
     * 登录用户
     */
    @Value("${mq.coupon.user-name}")
    private String user;

    /**
     * 登录密码
     */
    @Value("${mq.coupon.password}")
    private String passWord;

    /**
     * 交换机名称
     */
    @Value("${mq.coupon.exchange-name}")
    private String couponExchangeName;

    /**
     * 队列名称
     */
    @Value("${mq.coupon.queue-name}")
    private String queueName;

    /**
     * 路由key 同时也是bindKey 因为交换机是direct类型
     */
    @Value("${mq.coupon.route-key}")
    private String routeKey;

    /**
     * 配置链接信息
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostName, port);

        connectionFactory.setUsername(user);
        connectionFactory.setPassword(passWord);
        //connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);//发送确认，生产者与mq之间消息确认，保证消息的可靠传输
        return connectionFactory;
    }

    /**
     * 配置交换机
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(couponExchangeName, true, false);
    }

    /**
     * 配置消息队列
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);

    }

    /**
     * direct类型交换机，bindKey 与 route key 一样
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(routeKey);
    }
    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin admin=new RabbitAdmin(connectionFactory());
//        admin.declareExchange(defaultExchange());
        admin.declareQueue(queue());
        admin.declareBinding(binding());
        admin.getRabbitTemplate().setMessageConverter(integrationEventMessageConverter());
        return admin;
    }

    @Bean
    public AmqpTemplate rabbitTemplate() {

        return rabbitAdmin().getRabbitTemplate();
    }

    @Bean(name="listenerContainer")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(integrationEventMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//NONE：消息从queue发出，即认为处理成功，从队列删除  MANUAL:需要手动调用channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);确认消息处理成功
        factory.setPrefetchCount(1);
        return factory;
    }
    @Bean
    public MessageConverter integrationEventMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
