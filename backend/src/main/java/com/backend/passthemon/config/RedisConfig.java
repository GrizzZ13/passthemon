package com.backend.passthemon.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    @Value("${spring.redis.cluster.nodes}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;

    /**
     * 集群配置信息
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] hosts = host.split(",");
        Set<RedisNode> nodeList = new HashSet<>();
        for(String hostAndPort : hosts){
            String[] hostPort = hostAndPort.split(":");
            nodeList.add(new RedisNode(hostPort[0], Integer.parseInt(hostPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodeList);
        redisClusterConfiguration.setPassword(RedisPassword.of(password));
        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        return redisClusterConfiguration;
    }

    /**
     * connection pool configuration
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        return poolConfig;
    }

    /**
     * @return jedisCluster
     */
    @Bean
    public JedisCluster jedisCluster(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig){
        Set<HostAndPort> set = new HashSet<>();
        Set<RedisNode> nodes = redisClusterConfiguration.getClusterNodes();
        for (RedisNode node : nodes){
            set.add(new HostAndPort(node.getHost(), node.getPort()));
        }
        return new JedisCluster(set, 2000, 2000,5, "SJTUse2021", jedisPoolConfig);
    }

    /**
     * @return jedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 设置过期时间 6 小时
                        .entryTtl(Duration.ofHours(6L))
                        // 禁止缓存 null 值
                        .disableCachingNullValues()
                        // 设置 key 序列化
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        // 设置 value 序列化
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        RedisCacheConfiguration redisCacheConfiguration_verification =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 设置过期时间 17 minutes
                        .entryTtl(Duration.ofMinutes(17L))
                        // 禁止缓存 null 值
                        .disableCachingNullValues()
                        // 设置 key 序列化
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        // 设置 value 序列化
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        RedisCacheConfiguration redisCacheConfiguration_chatDto =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 设置过期时间 4h
                        .entryTtl(Duration.ofHours(4L))
                        // 禁止缓存 null 值
                        .disableCachingNullValues()
                        // 设置 key 序列化
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        // 设置 value 序列化
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(jedisConnectionFactory)
                // 缓存配置
                .cacheDefaults(redisCacheConfiguration)
                .withCacheConfiguration("user", redisCacheConfiguration)
                .withCacheConfiguration("channel", redisCacheConfiguration)
                // 配置同步修改或删除 put/evict
                .transactionAware()
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(RedisTemplate<String, Object> redisTemplate) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    }
}
