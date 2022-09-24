package com.paopao.user.cache;

import com.paopao.init.AbstractCache;
import com.paopao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 111
 * @Date 2022/9/24 17:34
 * @Description 例子：User预热
 */
@Component
public class SysUserCache extends AbstractCache {

    private static final String SYS_USER_CACHE_KEY = "SYS_USER";


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void initCache() {
        redisTemplate.opsForValue().set(SYS_USER_CACHE_KEY,"用户缓存");
    }

    @Override
    public <T> T getCache(String key) {
        if(!redisTemplate.hasKey(key).booleanValue()){
            reloadCache();
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void clearCache() {
        redisTemplate.delete(SYS_USER_CACHE_KEY);
    }
}
