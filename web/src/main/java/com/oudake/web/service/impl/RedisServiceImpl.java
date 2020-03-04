package com.oudake.web.service.impl;

import com.oudake.web.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangyi
 */
@Service
public class RedisServiceImpl implements RedisService {

    // 默认过期时间为1天
    private final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    // 无期限过期
    private final static long NOT_EXPIRE = -1;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断key是否存在
     * @param key key
     * @return boolean
     */
    @Override
    public boolean isKeyExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除key
     * @param key key
     */
    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     * @param keys keys
     */
    @Override
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     * @param keys keys
     */
    @Override
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     * @param oldKey oldKey
     * @param newKey newKey
     */
    @Override
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     * @param oldKey oldKey
     * @param newKey newKey
     * @return 修改成功返回true
     */
    @Override
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 设置key的生命周期
     * @param key key
     * @param time time
     * @param timeUnit timeUnit
     */
    @Override
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     * @param key key
     * @param date date
     */
    @Override
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     * @param key key
     * @param timeUnit timeUnit
     * @return
     */
    @Override
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     * @param key key
     */
    @Override
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }
}
