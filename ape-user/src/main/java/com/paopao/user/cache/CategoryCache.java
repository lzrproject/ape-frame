package com.paopao.user.cache;

import com.paopao.init.AbstractCache;
import org.springframework.stereotype.Component;

/**
 * @Author 111
 * @Date 2022/9/24 18:13
 * @Description 例子：分类预热
 */
@Component
public class CategoryCache extends AbstractCache {

    public static final String CATEGORY_CACHE_KEY = "category";

    @Override
    public void initCache() {
        redisTemplate.opsForValue().set(CATEGORY_CACHE_KEY,"购物车缓存");
    }

    @Override
    public <T> T getCache(String key) {
        return super.getCache(key);
    }

    @Override
    public void clearCache() {
        super.clearCache();
    }
}
