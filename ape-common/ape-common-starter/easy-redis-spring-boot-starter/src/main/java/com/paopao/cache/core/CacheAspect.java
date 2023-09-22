package com.paopao.cache.core;

import com.paopao.cache.bizAnnotation.BizDoubleCache;
import com.paopao.cache.bizAnnotation.BizLocalCache;
import com.paopao.cache.bizAnnotation.BizRedisCache;
import com.paopao.cache.bizAnnotation.ClearCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 缓存切面
 *
 * @author loser
 * @date 2023/06/20
 */
@Aspect
public class CacheAspect {

    private final CacheManger cacheManger;

    public CacheAspect(CacheManger cacheManger) {
        this.cacheManger = cacheManger;
    }

    /**
     * 标记这方法优先使用redis缓存
     */
    @Pointcut("@annotation(com.loser.cache.bizAnnotation.BizRedisCache)")
    private void markUseRedisCache() {
    }

    /**
     * 缓存切面
     *
     * @param bizRedisCache 缓存类
     */
    @Around(value = "markUseRedisCache() && @annotation(bizRedisCache)")
    public Object redisCacheHandler(ProceedingJoinPoint pjd, BizRedisCache bizRedisCache) throws Throwable {
        return cacheManger.redisCacheHandler(pjd, bizRedisCache);
    }

    /**
     * 标记这方法优先使用本地缓存
     */
    @Pointcut("@annotation(com.loser.cache.bizAnnotation.BizLocalCache)")
    private void markUseLocalCache() {
    }

    /**
     * 缓存切面
     *
     * @param bizLocalCache 缓存类
     */
    @Around(value = "markUseLocalCache() && @annotation(bizLocalCache)")
    public Object localCacheHandler(ProceedingJoinPoint pjd, BizLocalCache bizLocalCache) throws Throwable {
        return cacheManger.localCacheHandler(pjd, bizLocalCache);
    }

    /**
     * 标记这方法优先使用本地缓存
     */
    @Pointcut("@annotation(com.loser.cache.bizAnnotation.BizDoubleCache)")
    private void markUseDoubleCache() {
    }

    /**
     * 缓存切面
     *
     * @param bizDoubleCache 缓存类
     */
    @Around(value = "markUseDoubleCache() && @annotation(bizDoubleCache)")
    public Object doubleCacheHandler(ProceedingJoinPoint pjd, BizDoubleCache bizDoubleCache) throws Throwable {
        return cacheManger.doubleCacheHandler(pjd, bizDoubleCache);
    }

    /**
     * 标记清除缓存
     */
    @Pointcut("@annotation(com.loser.cache.bizAnnotation.ClearCache)")
    private void markClearCache() {
    }

    /**
     * 清除缓切面
     *
     * @param clearCache 缓存类
     */
    @Around(value = "markClearCache() && @annotation(clearCache)")
    public Object clearCacheHandler(ProceedingJoinPoint pjd, ClearCache clearCache) throws Throwable {
        return cacheManger.clearCacheHandler(pjd, clearCache);
    }

}
