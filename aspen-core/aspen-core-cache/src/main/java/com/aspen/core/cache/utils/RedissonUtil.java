package com.aspen.core.cache.utils;

import com.aspen.core.foundation.utils.SpringContextUtil;
import org.redisson.api.RedissonClient;

/**
 * Redisson工具类
 * @author jingchuan
 */
public class RedissonUtil {

    private static RedissonClient redissonClient;

    public static RedissonClient getRedisson() {
        if (redissonClient == null) {
            redissonClient = SpringContextUtil.getBean("redissonClient");
        }
        return redissonClient;
    }



}