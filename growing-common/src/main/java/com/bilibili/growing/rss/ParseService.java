package com.bilibili.growing.rss;

import com.bilibili.growing.entity.JdEntity;

import java.util.List;

public interface ParseService {
    Integer getData(JdEntity entity);
    Integer insertBatch (List<JdEntity> entities);
}
