package com.ayu.growing.rss;

import com.ayu.growing.entity.PayOrderEntity;

public interface HelloService {
    Integer add ();

    Integer update ();

    PayOrderEntity query(String id);
}
