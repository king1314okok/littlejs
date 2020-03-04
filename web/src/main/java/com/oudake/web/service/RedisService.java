package com.oudake.web.service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyi
 */
public interface RedisService {
    boolean isKeyExist(String key);

    void deleteKey(String key);

    void deleteKey(String... keys);

    void deleteKey(Collection<String> keys);

    void renameKey(String oldKey, String newKey);

    boolean renameKeyNotExist(String oldKey, String newKey);

    void expireKey(String key, long time, TimeUnit timeUnit);

    void expireKeyAt(String key, Date date);

    long getKeyExpire(String key, TimeUnit timeUnit);

    void persistKey(String key);
}
