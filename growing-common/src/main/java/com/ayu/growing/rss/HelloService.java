package com.bilibili.growing.rss;

import com.bilibili.growing.entity.PayOrderEntity;

public interface HelloService {
    Integer add ();

    Integer update ();

    PayOrderEntity query(String id);
}
