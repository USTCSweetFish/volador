package com.ayu.growing.rss;


public interface RabbitMqService {

     boolean produce(Object obj);

     boolean consume(Object obj);
}
