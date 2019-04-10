package com.bilibili.growing.feign;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Project: payplatform-pay
 * Author : chris ran
 * Time   : 2017/8/4
 * Desc   : redis操作
 */

public interface RedisService<T> {

    RedisTemplate getRedisTemplate();

    public boolean setIfNoExists(String key, String value);

    public boolean expire(String key, Long timeout);

    public boolean expireAt(String key, Date date);

    public void putToValue(String key, T value);

    public void putToValueExc(String key, T value, Long time, TimeUnit timeUnit);

    public void putToValue(String key, T value, Long time, TimeUnit timeUnit);

    public T getFromValue(final String key);

    public void removeCache(String key);

    public Long increment(String key, Long value, Long timeout, TimeUnit timeUnit);

    public Long incrementHash(String key, String hashKey, Long value);

    public Long increment(String key, Long value);

    public Double incrementDouble(String key, double value);

    public List<Object> leftGetAllList(String key);

    public long putToList(String key, Object value);

    public boolean putToSortSet(String key, Object value, double score);

    public Set getFromSortSet(String key, long start);

    public List multiGetHash(String key, Set hashKey);

    public long putToSortSet(String cacheKey, Set<DefaultTypedTuple> defaultTypedTuples);

    public long getZsetRank(String cacheKey, Object member);

    public void putToHash(String key, String hashKey, Object value);

    void putBoundHashWithExpire(final String key, final Map<String, String> dataMap,
                                final long time, final TimeUnit timeUnit);

    Map<String, String> getBoundHashEntries(String key);

    T getFromHash(String key, String hashKey);

    void putToHash(String key, String hashKey, T value, Long time, TimeUnit timeUnit);

    long getExpire(String key, TimeUnit timeUnit);

    Boolean lock(String key, Long expTime);
}