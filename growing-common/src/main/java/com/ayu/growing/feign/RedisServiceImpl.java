package com.ayu.growing.feign;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Project: payplatform-pay
 * Author : chris ran
 * Time   : 2017/8/4
 * Desc   : redis操作实现
 */

@Repository
public class RedisServiceImpl<T> implements RedisService<T> {

    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public boolean setIfNoExists(String key, String value) {
        boolean result = false;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
//            BiliLog.error("Bili Redis Cache opsForVlaue setnx Error {}", e.getMessage());
        }
        return result;
    }

    @Override
    public boolean expire(String key, Long timeout) {
        boolean result = false;
        try {
            result = redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
//            BiliLog.error("Bili Redis Cache expire Error {}", e.getMessage());
        }
        return result;
    }

    @Override
    public boolean expireAt(String key, Date date) {
        boolean result = false;
        try {
            result = redisTemplate.expireAt(key, date);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public T getFromValue(String key) {
        T result = null;
        try {
            result = (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForValue Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public void putToValue(String key, T value, Long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForObject Error! %s", e.getMessage()), e);
        }
    }

    @Override
    public void putToValue(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForObject Error! %s", e.getMessage()), e);
        }

    }

    @Override
    public void putToValueExc(String key, T value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public Long increment(String key, Long value, Long timeout, TimeUnit timeUnit) {
        Long result = null;
        try {
            result = redisTemplate.opsForValue().increment(key, value);
            redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public Long increment(String key, Long value) {
        Long result = null;
        try {
            result = redisTemplate.opsForValue().increment(key, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public Double incrementDouble(String key, double value) {
        Double result = null;
        try {
            result = redisTemplate.opsForValue().increment(key, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public long putToList(String key, Object value) {
        long result = 0;
        try {
            result = redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }


    @Override
    public boolean putToSortSet(String key, Object value, double score) {
        boolean result = false;
        try {
            result = redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }


    @Override
    public void putToHash(String key, String hashKey, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForHash put Error! %s", e.getMessage()), e);
        }
    }

    @Override
    public List multiGetHash(String key, Set hashKey) {
        List result = null;
        try {
            result = redisTemplate.opsForHash().multiGet(key, hashKey);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public Set getFromSortSet(String key, long length) {
        Set result = null;
        try {
            result = redisTemplate.opsForZSet().range(key, 0, length - 1);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public long putToSortSet(String key, Set<DefaultTypedTuple> defaultTypedTuples) {
        long result = 0;
        try {
            result = redisTemplate.opsForZSet().add(key, defaultTypedTuples);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public long getZsetRank(String key, Object member) {
        long result = 0;
        try {
            result = redisTemplate.opsForZSet().rank(key, member);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForZSet rank Error! %s", e.getMessage()), e);
        }
        return result;
    }


    @Override
    public List<Object> leftGetAllList(String key) {
        List<Object> result = null;
        try {
            result = redisTemplate.opsForList().range(key, 0L, -1L);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public Long incrementHash(String key, String hashKey, Long value) {
        Long result = null;
        try {
            result = redisTemplate.opsForHash().increment(key, hashKey, value);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForHash increment Error! %s", e.getMessage()), e);
        }
        return result;
    }

    @Override
    public void removeCache(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void putBoundHashWithExpire(String key, Map<String, String> dataMap, long time, TimeUnit timeUnit) {
        try {
            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);

            boundHashOperations.putAll(dataMap);
            boundHashOperations.expire(time, timeUnit);
        } catch (Exception e) {
//            BiliLog.error(String.format("Redis Cache putBoundHashWithExpire Error! %s", e.getMessage()), e);
        }
    }

    @Override
    public Map<String, String> getBoundHashEntries(String key) {
        try {
            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
            return boundHashOperations.entries();
        } catch (Exception e) {
//            BiliLog.error(String.format("Redis Cache getBoundHashEntries Error! %s", e.getMessage()), e);
            return new HashMap<>();
        }
    }

    @Override
    public T getFromHash(String key, String hashKey) {
        try {
            return (T) redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
        }
        return null;
    }

    @Override
    public void putToHash(String key, String hashKey, T value, Long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache opsForHash put Error! %s", e.getMessage()), e);
        }
    }

    @Override
    public long getExpire(String key, TimeUnit timeUnit) {
        long remaining = 0l;
        try {
            remaining = redisTemplate.getExpire(key, timeUnit);
        } catch (Exception e) {
//            BiliLog.error(String.format("Bili Redis Cache getExpire Error! %s", e.getMessage()), e);
        }
        return remaining;
    }

    @Override
    public Boolean lock(String key, Long expireTime) {

        try {
            RedisCallback<String> callback = (con) -> {
                JedisCommands commands = (JedisCommands) con.getNativeConnection();
                String uuid = UUID.randomUUID().toString();
                return commands.set(key, uuid, "NX", "PX", expireTime);
            };
            String result = redisTemplate.execute(callback).toString();
            return !StringUtils.isEmpty(result);
        } catch (Exception ex) {
//            BiliLog.error("getLock fail!msg=[{}]", ex.getMessage());
        }
        return false;

    }

}