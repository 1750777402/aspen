package com.aspen.core.cache.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig {

    private RedisProperties redisProperties;

    @Autowired
    public RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    //最大空闲连接数
    private static final int MAX_IDLE = 50;
    //最大连接数
    private static final int MAX_TOTAL = 100;
    //建立连接最长等待时间
    private static final long MAX_WAIT_MILLIS = 5000;


    /**
     * 配置工厂
     */
    private RedisConnectionFactory connectionFactory(String host, int port, String password) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);

        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        jedisConnectionFactory.setPoolConfig(poolConfig());
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    //连接池配置
    private JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setMaxTotal(MAX_TOTAL);
        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
        poolConfig.setTestOnBorrow(false);
        return poolConfig;
    }

    @Primary
    @Bean(name = "redisTemplateMaster")
    public StringRedisTemplate redisTemplateMaster() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory(redisProperties.getHost(), redisProperties.getPort(), redisProperties.getPassword()));
        return template;
    }

}