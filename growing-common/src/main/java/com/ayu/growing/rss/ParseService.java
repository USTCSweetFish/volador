package com.ayu.growing.rss;

import com.ayu.growing.entity.JdEntity;

import java.util.List;

public interface ParseService {
    Integer getData(JdEntity entity);
    Integer insertBatch (List<JdEntity> entities);
}
