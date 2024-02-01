package com.aspen.core.cache.utils;

import com.alibaba.fastjson.JSONObject;
import com.aspen.core.cache.contants.CacheConstant;
import com.aspen.core.foundation.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis工具类
 *
 * @author jingchuan
 */
@Slf4j
public class RedisUtil {

    private static StringRedisTemplate redisClient;

    public static StringRedisTemplate getRedisClient() {
        if (redisClient == null) {
            redisClient = SpringContextUtil.getBean("redisTemplateMaster");
        }
        return redisClient;
    }

    /**
     * 缓存一天
     * 存储数据
     */
    public static void set(String key, String value) {
        set(key, value, CacheConstant.HOURS_24);
    }

    /**
     * 存储数据
     * 定义缓存超时时间
     */
    public static void set(String key, String value, Long expireTime) {
        getRedisClient().opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 永久保存
     */
    public static void setForever(String key, String value) {
        getRedisClient().opsForValue().set(key, value);
    }

    /**
     * 获取过期时间
     */
    public static Long getExpire(String key) {
        return getRedisClient().getExpire(key);
    }

    /**
     * 推送消息
     */
    public static void pushMessage(String topic, String message) {
        getRedisClient().opsForList().leftPush(topic, message);
    }

    /**
     * 拉取消息
     */
    public static String popMessage(String topic) {
        return getRedisClient().opsForList().rightPop(topic);
    }

    /**
     * 拉取第一个 不删除
     */
    public static String firstMessage(String topic) {
        List<String> messsages = getRedisClient().opsForList().range(topic, 0, 0);
        if (CollectionUtils.isNotEmpty(messsages)) {
            return messsages.get(0);
        }
        return null;
    }

    /**
     * 队列长度
     */
    public static Long listSize(String topic) {
        return getRedisClient().opsForList().size(topic);
    }

    /**
     * 获取列表指定范围内的元素
     */
    public static List<String> lRange(String key, int start, int stop) {
        List<String> list = getRedisClient().opsForList().range(key, start, stop);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 存储对象数据   默认存储一天
     */
    public static void setObj(String key, Object value) {
        setObj(key, value, CacheConstant.HOURS_24);
    }

    /**
     * 存储对象数据   带过期时间
     */
    public static void setObj(String key, Object value, Long exp) {
        getRedisClient().opsForValue().set(key, JSONObject.toJSONString(value), exp, TimeUnit.SECONDS);
    }

    /**
     * 获取数据
     */
    public static String get(String key) {
        return getRedisClient().opsForValue().get(key);
    }

    /**
     * 获取对象
     */
    public static <T> T getObj(String key, Class<T> cls) {
        String data = get(key);
        try {
            if (!StringUtils.isEmpty(data)) {
                return JSONObject.parseObject(data, cls);
            }
        } catch (Throwable e) {
            log.error("get obj failed : {}, {}", key, data);
        }
        return null;
    }

    /**
     * 批量获取
     */
    public static List<String> multiGet(List<String> keys) {
        return getRedisClient().opsForValue().multiGet(keys);
    }

    /**
     * 批量获取对象
     */
    public static <T> List<T> multiGetObj(List<String> keys, Class<T> cls) {
        List<String> data = multiGet(keys);
        if (CollectionUtils.isNotEmpty(data)) {
            return data.stream().map(str -> JSONObject.parseObject(str, cls)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 设置超期时间
     */
    public static boolean expire(String key, long expire) {
        return getRedisClient().expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除数据
     */
    public static void remove(String key) {
        getRedisClient().delete(key);
    }

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    public static Long increment(String key, long delta) {
        return getRedisClient().opsForValue().increment(key, delta);
    }

    /**
     * 自减操作
     *
     * @param delta 自减步长
     */
    public static Long decrement(String key, long delta) {
        return getRedisClient().opsForValue().decrement(key, delta);
    }

    /**
     * 获取集合对象数据
     */
    public static <T> List<T> getArrayList(String key, Class<T> cls) {
        String data = get(key);
        try {
            if (!StringUtils.isEmpty(data)) {
                return JSONObject.parseArray(data, cls);
            }
        } catch (Throwable e) {
            log.error("get arrayList failed : {}, {}", key, data);
        }
        return null;
    }

    /**
     * 向有序集合添加一个，或者更新已存在成员的分数
     */
    public static Boolean zSetAdd(String key, double score, String member) {
        return getRedisClient().opsForZSet().add(key, member, score);
    }

    /**
     * 向有序集合添加多个成员，或者更新已存在成员的分数
     */
    public static Long zSetAddList(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return getRedisClient().opsForZSet().add(key, tuples);
    }

    /**
     * 对集合中的某个排序值增量incrementScore
     *
     * @param key            有序集合对key
     * @param incrementScore 增量值
     * @param member         集合中的唯一标识
     */
    public static Double zSetIncrement(String key, double incrementScore, String member) {
        return getRedisClient().opsForZSet().incrementScore(key, member, incrementScore);
    }

    /**
     * 获取有序队列中的某个元素的分数
     *
     * @param key    有序集合的key
     * @param member 集合中的唯一标识
     */
    public static Double getZSetScore(String key, String member) {
        return getRedisClient().opsForZSet().score(key, member);
    }

    /**
     * 获取key对应有序集合的总数
     */
    public static Long getZsetSize(String key) {
        return getRedisClient().opsForZSet().zCard(key);
    }

    /**
     * 从大到小获取第start到第end的集合中的元素 比如获取这个key的从大到小的前十名，strat为0，end为9
     *
     * @param key   有序集合的key
     * @param start 开始元素排名
     * @param end   结束元素排名
     */
    public static Set<ZSetOperations.TypedTuple<String>> zSetReverseRangeWithScores(String key, long start, long end) {
        return getRedisClient().opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 删除zset集合中的某个元素 返回被删除元素个数
     */
    public static Long deleteZsetMember(String key, String... member) {
        return getRedisClient().opsForZSet().remove(key, member);
    }

    /**
     * 查看key是否存在
     */
    public static Boolean existsKey(String key) {
        return getRedisClient().hasKey(key);
    }

    /**
     * 批量缓存
     */
    public static void multiSet(Map<String, String> param) {
        getRedisClient().opsForValue().multiSet(param);
    }

    /**
     * 设置哈希值
     */
    public static void hSet(String key, String hKey, Object value) {
        getRedisClient().opsForHash().put(key, hKey, value);
    }

    /**
     * 获取哈希值
     */
    public static Object hGet(String key, String hKey) {
        return getRedisClient().opsForHash().get(key, hKey);
    }

    /**
     * set集合添加一个元素
     * @return 是否添加成功 也有可能是set中已有value 所以会是false
     */
    public static boolean sAdd(String key, String value) {
        Long add = getRedisClient().opsForSet().add(key, value);
        if (add == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取set集合的长度
     */
    public static long scard(String key) {
        Long size = getRedisClient().opsForSet().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 从set中随机取几个元素
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     */
    public static List<String> sRandMember(String key, Integer count) {
        return getRedisClient().opsForSet().randomMembers(key, count);
    }

}