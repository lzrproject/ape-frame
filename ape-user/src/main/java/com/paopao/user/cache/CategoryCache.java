package com.paopao.user.cache;

import com.paopao.init.AbstractCache;
import com.paopao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 111
 * @Date 2022/9/24 18:13
 * @Description 例子：分类预热
 */
@Component
public class CategoryCache extends AbstractCache {

    public static final String CATEGORY_CACHE_KEY = "category";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void initCache() {
        //跟数据库做联动了，跟其他的数据来源进行联动
//        redisUtil.set("category","知识");
        redisTemplate.opsForValue().set(CATEGORY_CACHE_KEY,"购物车缓存");
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
        super.clearCache();
    }
}
