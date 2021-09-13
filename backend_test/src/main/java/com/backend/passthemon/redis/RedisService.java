package com.backend.passthemon.redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    boolean set(final String key, Object value);
    boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit);
    void remove(final String... keys);
    void removePattern(final String pattern);
    void remove(final String key);
    boolean exists(final String key);
    Object get(final String key);
    void hmSet(String key, Object hashKey, Object value);
    Object hmGet(String key, Object hashKey);
    void rPush(String k, Object v);
    List<Object> range(String k, long l, long l1);
    void add(String key, Object value);
    Set<Object> setMembers(String key);
    void removeAll(String keyPrefix);
    Set<String> keys(String keyPrefix);
}
