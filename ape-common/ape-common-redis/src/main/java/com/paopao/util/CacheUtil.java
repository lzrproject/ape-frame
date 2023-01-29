package com.paopao.util;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @Author: paoPao
 * @Date: 2023/1/29
 * @Description: guava+泛型实战
 */
@Component
@Slf4j
public class CacheUtil<K, V> {

    // 本地缓存开关
    @Value("${guava.cache.switch}")
    public Boolean switchCache;

    // 初始化本地缓存
    private Cache<String, String> localCache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    /**
     * 获取结果数据
     * @param skuIdList 查询参数list
     * @param cachePrefix 前缀
     * @param clazz 泛型
     * @param function 函数式编程
     * @return Map
     */
    public Map<K, V> getResult(List<K> skuIdList, String cachePrefix,
                               Class<V> clazz, Function<List<K>, Map<K, V>> function) {
        // 参数校验
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Collections.emptyMap();
        }
        // 声明结果
        Map<K, V> resultMap = new HashMap<>(16);
        // 如果关闭本地缓存，走函数式编程
        if (!switchCache) {
            resultMap = function.apply(skuIdList);
            return resultMap;
        }
        List<K> noCacheList = new ArrayList<>();
        for (K id : skuIdList) {
            String cacheKey = cachePrefix + "_" + id;
            String content = localCache.getIfPresent(cacheKey);
            // 判断本地缓存是否存在
            if (StringUtils.isNotBlank(content)) {
                V value = JSON.parseObject(content, clazz);
                resultMap.put(id, value);
            } else {
                noCacheList.add(id);
            }
        }
        if (CollectionUtils.isEmpty(noCacheList)) {
            return resultMap;
        }
        // 查询函数式是否存在数据
        Map<K, V> cacheMap = function.apply(noCacheList);
        if (CollectionUtils.isEmpty(cacheMap)) {
            return cacheMap;
        }
        // 存在数据则存入本地缓存
        for (Map.Entry<K, V> entry : cacheMap.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            String cacheKey = cachePrefix + "_" + key;
            localCache.put(cacheKey, JSON.toJSONString(value));
            resultMap.put(key, value);
        }
        return resultMap;
    }
}
