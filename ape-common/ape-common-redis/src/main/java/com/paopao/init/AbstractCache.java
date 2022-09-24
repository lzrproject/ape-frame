package com.paopao.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 111
 * @Date 2022/9/24 16:01
 * @Description 缓存预热
 */
@Component
public abstract class AbstractCache {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 初始化缓存
     */
    public void initCache(){

    }

    /**
     * 获取缓存
     * @param key 缓存key
     * @param <T> 泛型
     * @return T
     */
    public <T> T getCache(String key) {
        if(!redisTemplate.hasKey(key).booleanValue()){
            reloadCache();
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 清除缓存
     */
    public void clearCache() {

    }

    /**
     * 刷新缓存
     */
    public void reloadCache() {
        clearCache();
        initCache();
    }
}
