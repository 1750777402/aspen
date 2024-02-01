package com.aspen.core.cache.config;

import com.aspen.core.foundation.contants.CoreConstants;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jingchuan
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {

    private static final String REDIS_PROTOCOL_NAME = "redis://";

    private RedisProperties redisProperties;

    @Autowired
    public RedissonConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config redissonConfig = new Config();
        redissonConfig
                .useSingleServer()
                .setAddress(
                        REDIS_PROTOCOL_NAME.concat(redisProperties.getHost())
                                .concat(CoreConstants.COLON)
                                .concat(String.valueOf(redisProperties.getPort())))
                .setDatabase(redisProperties.getDatabase())
                .setPassword(redisProperties.getPassword())
                .setClientName("SimpleRedisson");
        return Redisson.create(redissonConfig);
    }

}