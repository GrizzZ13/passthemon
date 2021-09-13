package com.backend.passthemon.serviceimpl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.backend.passthemon.service.RedisService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.jedis.util.JedisClusterCRC16;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 写入缓存
     */
    @Override
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     */
    @Override
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean setIfAbsent(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value, expireTime, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除对应的value
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     */
    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 列表添加 rightPush
     */
    @Override
    public void rPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
        log.info("[Redis Service] : right push");
    }

    @Override
    public List<Object> range(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     */
    @Override
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

//    /**
//     *  获取指定前缀的一系列key
//     *  使用scan命令代替keys, Redis是单线程处理，keys命令在KEY数量较多时，
//     *  操作效率极低【时间复杂度为O(N)】，该命令一旦执行会严重阻塞线上其它命令的正常请求
//     * @param keyPrefix : prefix
//     * @return keys
//     */
//    public Set<String> keys(String keyPrefix) {
//        String realKey = keyPrefix + "*";
//        try {
//            return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
//                Set<String> binaryKeys = new HashSet<>();
//                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(realKey).count(Integer.MAX_VALUE).build());
//                while (cursor.hasNext()) {
//                    binaryKeys.add(new String(cursor.next()));
//                }
//                return binaryKeys;
//            });
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public List<String> getScan(Jedis jedis, String key){
        List<String> list = new ArrayList<>();
        ScanParams params = new ScanParams();
        params.match(key);
        params.count(Integer.MAX_VALUE);
        String cursor = "0";
        do {
            ScanResult<String> scanResult = jedis.scan(cursor, params);
            List<String> elements = scanResult.getResult();
            if (elements != null && elements.size() > 0) {
                list.addAll(elements);
            }
            cursor = scanResult.getCursor();
        } while (!"0".equals(cursor));
        return list;
    }

    @Override
    public List<String> getRedisKeys(String keyPrefix){
        String matchKey = keyPrefix + "*";
        List<String> list = new ArrayList<>();
        try{
            Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
            for(Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
                Jedis jedis = entry.getValue().getResource();
                // 判断非从节点(因为若主从复制，从节点会跟随主节点的变化而变化)
                if (!jedis.info("replication").contains("role:slave")) {
                    List<String> keys = getScan(jedis, matchKey);
                    if (keys.size() > 0) {
                        Map<Integer, List<String>> map = new HashMap<>();
                        for (String key : keys) {
                            // cluster模式执行多key操作的时候，这些key必须在同一个slot上，不然会报:JedisDataException:
                            // CROSS SLOT Keys in request don't hash to the same slot
                            int slot = JedisClusterCRC16.getSlot(key);
                            // 按slot将key分组，相同slot的key一起提交
                            if (map.containsKey(slot)) {
                                map.get(slot).add(key);
                            } else {
                                map.put(slot, Lists.newArrayList(key));
                            }
                        }
                        for (Map.Entry<Integer, List<String>> integerListEntry : map.entrySet()) {
                            list.addAll(integerListEntry.getValue());
                        }
                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return list;
        }
    }
}

