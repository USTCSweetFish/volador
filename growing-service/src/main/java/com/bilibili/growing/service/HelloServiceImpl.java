package com.bilibili.growing.service;

import com.bilibili.growing.domain.HelloDomain;
import com.bilibili.growing.entity.PayOrderEntity;
import com.bilibili.growing.rss.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    private HelloDomain helloDomain;
    @Override
    public Integer add() {
        PayOrderEntity payOrderEntity = new PayOrderEntity();
        return helloDomain.insertOrder(payOrderEntity);
    }

    @Override
    public Integer update() {
        PayOrderEntity payOrderEntity = new PayOrderEntity();
        return helloDomain.updateOrder(payOrderEntity,1L);
    }



    //公共配置表查询可以走缓存，节省数据库资源
    @Cacheable(value = "commonConfigCache"
            , key = "#id"
            , condition = "#id !=null and #id!=''"
            , unless = " #result==null")
    @Override
    public PayOrderEntity query(String id) {
        return null;
    }
}
