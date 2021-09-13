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
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 连接池配置信息
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(maxActive);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(minIdle);
        //最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //当池内没有可用的连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(0);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(port);
        //获得默认的连接池构造器
        //指定jedisPoolConifig来修改默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
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
                .fromConnectionFactory(redisConnectionFactory)
                // 缓存配置
                .cacheDefaults(redisCacheConfiguration)
                .withCacheConfiguration("verification", redisCacheConfiguration_verification)
                .withCacheConfiguration("chatDto", redisCacheConfiguration_chatDto)
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
