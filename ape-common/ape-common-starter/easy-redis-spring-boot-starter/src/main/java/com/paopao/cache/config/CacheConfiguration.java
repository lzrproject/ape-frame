package com.paopao.cache.config;

import com.paopao.cache.core.CacheAspect;
import com.paopao.cache.core.CacheManger;
import com.paopao.redis.core.RedisHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * redis 配置类
 *
 * @author loser
 * @date 2023/06/20
 */
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    private final CacheProperties cacheProperties;

    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean("cacheAspect")
    public CacheAspect cacheAspect(CacheManger cacheManger) {
        return new CacheAspect(cacheManger);
    }

    @Bean
    public CacheManger actCacheManger(RedisHandler redisHandler) {
        return new CacheManger(redisHandler, cacheProperties);
    }

}
