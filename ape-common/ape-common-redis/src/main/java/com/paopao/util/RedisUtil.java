package com.paopao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Author 111
 * @Date 2022/9/24 15:57
 * @Description
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,time,timeUnit);
    }

    public String get(String key){
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    public boolean del(String key){
        return redisTemplate.delete(key);
    }
}
