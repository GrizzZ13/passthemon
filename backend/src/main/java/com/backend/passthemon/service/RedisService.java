package com.backend.passthemon.service;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    boolean set(final String key, Object value);
    boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit);
    boolean setIfAbsent(final String key, Object value, Long expireTime, TimeUnit timeUnit);
    void remove(final String key);
    boolean exists(final String key);
    Object get(final String key);
    void rPush(String k, Object v);
    List<Object> range(String k, long l, long l1);
    void add(String key, Object value);
    List<String> getScan(Jedis jedis, String key);
    List<String> getRedisKeys(String keyPrefix);
//    Set<String> keys(String keyPrefix);
}
