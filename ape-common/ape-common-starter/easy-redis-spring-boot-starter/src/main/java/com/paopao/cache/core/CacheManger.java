package com.paopao.cache.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.paopao.cache.bizAnnotation.*;
import com.paopao.cache.config.CacheProperties;
import com.paopao.cache.util.StrUtil;
import com.paopao.redis.core.RedisHandler;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPubSub;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 活动缓存类管理器
 *
 * @author loser
 * @date 2023/06/20
 */
public class CacheManger extends JedisPubSub {

    String CLEAR_CHANNEL = "EASY:CLEAR:LOCAL:CACHE";
    String NULL_OBJ = "{}";
    String NULL_LIST = "[]";

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(10, 50,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1000));

    /**
     * 标记清除缓存的关系
     */
    private static final Map<String, Result> METHOD_CACHE = new ConcurrentHashMap<>();

    /**
     * 标记清除缓存的关系
     */
    private static final Map<String, KeyResult> METHOD_KEY_CACHE = new ConcurrentHashMap<>();

    /**
     * 缓存 key 对应的返回值类型
     */
    private static final Map<String, Class<?>> KEY_CLASS = new ConcurrentHashMap<>();
    /**
     * 标注 key 对应的返回值是不是集合
     */
    private static final Map<String, Boolean> KEY_TYPE = new ConcurrentHashMap<>();
    /**
     * 缓存
     */
    private static final Cache<String, String> SECOND_3 = Caffeine.newBuilder().initialCapacity(16)
            .maximumSize(256).expireAfterWrite(Duration.ofSeconds(3)).build();
    /**
     * 缓存
     */
    private static final Cache<String, String> SECOND_10 = Caffeine.newBuilder().initialCapacity(32)
            .maximumSize(512).expireAfterWrite(Duration.ofSeconds(10)).build();
    /**
     * 缓存
     */
    private static final Cache<String, String> MIN_1 = Caffeine.newBuilder().initialCapacity(1024)
            .maximumSize(4096).expireAfterWrite(Duration.ofMinutes(1)).build();
    /**
     * 缓存
     */
    private static final Cache<String, String> MIN_5 = Caffeine.newBuilder().initialCapacity(32)
            .maximumSize(512).expireAfterWrite(Duration.ofMinutes(5)).build();
    /**
     * 缓存
     */
    private static final Cache<String, String> MIN_10 = Caffeine.newBuilder().initialCapacity(32)
            .maximumSize(512).expireAfterWrite(Duration.ofMinutes(10)).build();
    /**
     * 缓存
     */
    private static final Cache<String, String> HOUR_1 = Caffeine.newBuilder().initialCapacity(16)
            .maximumSize(256).expireAfterWrite(Duration.ofHours(1)).build();

    private final RedisHandler redisHandler;

    private final CacheProperties cacheProperties;

    public CacheManger(RedisHandler redisHandler, CacheProperties cacheProperties) {
        this.redisHandler = redisHandler;
        this.cacheProperties = cacheProperties;
        CacheManger cacheManger = this;
        // 使用redis的发布订阅模式 同步多节点本地缓存
        new Thread(() -> redisHandler.getJedis(0).subscribe(cacheManger, CLEAR_CHANNEL)).start();
    }

    /**
     * redis 缓存处理
     */
    public Object redisCacheHandler(ProceedingJoinPoint pjd, BizRedisCache bizRedisCache) throws Throwable {

        // 01 构建缓存相关key
        String classKey = buildClassKey(pjd, bizRedisCache.pre(), bizRedisCache.value());
        String cacheKey = buildCacheKey(pjd, classKey);

        // 02 获取redis缓存中的值
        String value = redisHandler.get(cacheKey);
        if (StrUtil.isNotEmpty(value)) {
            // 处理空值
            if (value.contentEquals(NULL_OBJ)) {
                return null;
            }
            Object result = buildResult4Str(classKey, value);
            if (Objects.nonNull(result)) {
                return result;
            }
        }

        // 03 从非缓存中获取 添加缓存 并标记 class 映射关系
        Object result = buildResultAndMark(pjd, classKey);
        if (Objects.nonNull(result)) {
            redisHandler.set(cacheKey, JSON.toJSONString(result), bizRedisCache.timeOut());
        }
        return result;

    }

    /**
     * local cache 处理器
     */
    public Object localCacheHandler(ProceedingJoinPoint pjd, BizLocalCache bizLocalCache) throws Throwable {

        // 01 构建缓存相关key
        Cache<String, String> localCache = getLocalCache(bizLocalCache.timeType());
        String classKey = buildClassKey(pjd, bizLocalCache.pre(), bizLocalCache.value());
        String cacheKey = buildCacheKey(pjd, classKey);

        // 02 获取本地缓存中的值
        String value = localCache.getIfPresent(cacheKey);
        if (StrUtil.isNotEmpty(value)) {
            // 处理空值
            if (NULL_OBJ.equals(value)) {
                return null;
            }
            assert value != null;
            Object result = buildResult4Str(classKey, value);
            if (Objects.nonNull(result)) {
                return result;
            }
        }

        // 03 从非缓存中获取 添加缓存 并标记 class 映射关系
        Object result = buildResultAndMark(pjd, classKey);
        if (Objects.nonNull(result)) {
            localCache.put(cacheKey, JSON.toJSONString(result));
        }
        return result;

    }

    /**
     * 双层缓存处理器
     */
    public Object doubleCacheHandler(ProceedingJoinPoint pjd, BizDoubleCache bizDoubleCache) throws Throwable {

        // 01 构建缓存相关key
        Cache<String, String> localCache = getLocalCache(bizDoubleCache.timeType());
        String classKey = buildClassKey(pjd, bizDoubleCache.pre(), bizDoubleCache.value());
        String cacheKey = buildCacheKey(pjd, classKey);

        // 02 获取本地缓存中的值
        String value = localCache.getIfPresent(cacheKey);
        if (StrUtil.isNotEmpty(value)) {
            // 处理空值
            if (NULL_OBJ.equals(value)) {
                return null;
            }
            assert value != null;
            Object result = buildResult4Str(classKey, value);
            if (Objects.nonNull(result)) {
                return result;
            }
        }

        // 03 获取redis缓存中的值 并添加到本地缓存中
        value = redisHandler.get(cacheKey);
        if (StrUtil.isNotEmpty(value)) {
            // 添加本地缓存
            localCache.put(cacheKey, value);
            // 处理空值
            if (value.contentEquals(NULL_OBJ)) {
                return null;
            }
            Object result = buildResult4Str(classKey, value);
            if (Objects.nonNull(result)) {
                return result;
            }
        }

        // 04 从非缓存中获取 添加缓存 并标记 class 映射关系
        Object result = buildResultAndMark(pjd, classKey);
        if (Objects.nonNull(result)) {
            localCache.put(cacheKey, JSON.toJSONString(result));
            redisHandler.set(cacheKey, JSON.toJSONString(result), bizDoubleCache.timeOut());
        }
        return result;

    }

    private String buildCacheKey(ProceedingJoinPoint pjd, String key) {

        Object[] args = pjd.getArgs();
        if (Objects.isNull(args) || args.length == 0) {
            return key;
        }
        StringBuilder sb = new StringBuilder(key);
        for (Object arg : args) {
            sb.append(":").append(arg);
        }
        return sb.toString();

    }

    /**
     * 从string中构建出返回值
     */
    private Object buildResult4Str(String key, String value) {

        if (value.contentEquals(NULL_LIST)) {
            return Collections.emptyList();
        }
        Class<?> tClass = KEY_CLASS.get(key);
        Boolean isCollection = KEY_TYPE.get(key);
        if (Objects.nonNull(tClass) && Objects.nonNull(isCollection)) {
            if (isCollection) {
                return JSON.parseArray(value, tClass);
            } else {
                return JSON.parseObject(value, tClass);
            }
        } else {
            return null;
        }

    }

    /**
     * 查询非缓存的数据
     */
    private Object buildResultAndMark(ProceedingJoinPoint pjd, String key) throws Throwable {

        Object result = pjd.proceed();
        if (Objects.isNull(result)) {
            return null;
        }
        boolean isCollection = result instanceof List;
        if (!KEY_TYPE.containsKey(key)) {
            KEY_TYPE.put(key, isCollection);
        }
        if (!KEY_CLASS.containsKey(key)) {
            if (isCollection) {
                if (CollectionUtils.isEmpty((List<?>) result)) {
                    result = Collections.emptyList();
                } else {
                    KEY_CLASS.put(key, ((List<?>) result).get(0).getClass());
                }
            } else {
                KEY_CLASS.put(key, result.getClass());
            }
        }
        return result;

    }

    private Cache<String, String> getLocalCache(BizLocalCache.TimeType type) {

        if (Objects.isNull(type)) {
            throw new RuntimeException("缓存不存在");
        }
        switch (type) {
            case SECOND_3:
                return SECOND_3;
            case SECOND_10:
                return SECOND_10;
            case MIN_1:
                return MIN_1;
            case MIN_5:
                return MIN_5;
            case MIN_10:
                return MIN_10;
            case HOUR_1:
                return HOUR_1;
            default:
                throw new RuntimeException("缓存不存在");
        }

    }

    private String buildClassKey(ProceedingJoinPoint joinPoint, String pre, String key) {
        return buildClassKey(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), pre, key);
    }

    private String buildClassKey(String pack, String method, String pre, String key) {

        if (StrUtil.isEmpty(pre)) {
            pre = cacheProperties.getPre();
        }
        if (StrUtil.isNotEmpty(key)) {
            if (StrUtil.isNotEmpty(pre)) {
                return pre + key;
            }
            return key;
        }
        String declaringTypeName = pack.replaceAll(cacheProperties.getPack(), "");
        return (pre + declaringTypeName.replaceAll("\\.", ":") + ":" + method).toUpperCase();

    }

    public Object clearCacheHandler(ProceedingJoinPoint pjd, ClearCache clearCache) throws Throwable {

        try {
            return pjd.proceed();
        } finally {
            Object[] args = pjd.getArgs();
            if (Objects.nonNull(args) && args.length != 0) {
                Object arg = pjd.getArgs()[0];
                String className = pjd.getSignature().getDeclaringTypeName();
                String methodName = className + ":" + pjd.getSignature().getName();
                Result result = getResult(methodName, pjd, arg, clearCache);
                clearCache(className, arg, result.fieldMappers, result.method, result.method.getAnnotation(result.annotation));
            }
        }

    }

    /**
     * 获取方法删除缓存对应的方法及字段信息
     */
    private static Result getResult(String key, ProceedingJoinPoint pjd, Object arg, ClearCache clearCache) {

        Result result = METHOD_CACHE.get(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        synchronized (key) {
            result = METHOD_CACHE.get(key);
            if (Objects.nonNull(result)) {
                return result;
            }
            Method[] methods = pjd.getTarget().getClass().getMethods();
            List<FieldMapper> fieldMappers = new ArrayList<>();
            for (Field field : arg.getClass().getDeclaredFields()) {
                MapperKey mapperKey = field.getAnnotation(MapperKey.class);
                if (Objects.nonNull(mapperKey)) {
                    List<FieldMapper> list = Arrays.stream(mapperKey.value()).map(item -> {
                        FieldMapper mapper = new FieldMapper();
                        field.setAccessible(true);
                        mapper.setField(field);
                        mapper.setMapperKey(item.key());
                        mapper.setSort(item.sort());
                        return mapper;
                    }).collect(Collectors.toList());
                    fieldMappers.addAll(list);
                }
            }
            String mapperKey = clearCache.value();
            Method getMethod = null;
            Class<? extends Annotation> annotation = null;
            for (Method method : methods) {
                BizLocalCache bizLocalCache = method.getAnnotation(BizLocalCache.class);
                if (Objects.nonNull(bizLocalCache) && (mapperKey.equals(bizLocalCache.mapperKey()) || mapperKey.equals(method.getName()))) {
                    getMethod = method;
                    annotation = BizLocalCache.class;
                    break;
                }
                BizRedisCache bizRedisCache = method.getAnnotation(BizRedisCache.class);
                if (Objects.nonNull(bizRedisCache) && (mapperKey.equals(bizRedisCache.mapperKey()) || mapperKey.equals(method.getName()))) {
                    getMethod = method;
                    annotation = BizRedisCache.class;
                    break;
                }
                BizDoubleCache bizDoubleCache = method.getAnnotation(BizDoubleCache.class);
                if (Objects.nonNull(bizDoubleCache) && (mapperKey.equals(bizDoubleCache.mapperKey()) || mapperKey.equals(method.getName()))) {
                    getMethod = method;
                    annotation = BizDoubleCache.class;
                    break;
                }
            }
            List<FieldMapper> list = fieldMappers.stream().filter(item -> item.getMapperKey().equals(mapperKey)).sorted(Comparator.comparing(FieldMapper::getSort)).collect(Collectors.toList());
            result = new Result(getMethod, list, annotation);
            METHOD_CACHE.put(key, result);
            return result;
        }

    }

    /**
     * 删除缓存信息
     */
    private void clearCache(String className, Object arg, List<FieldMapper> fieldMappers, Method method, Annotation annotation) throws IllegalAccessException {

        if (Objects.isNull(annotation) || CollectionUtils.isEmpty(fieldMappers)) {
            return;
        }
        if (annotation instanceof BizLocalCache) {
            BizLocalCache bizLocalCache = (BizLocalCache) annotation;
            String key = getCacheKey(className, arg, fieldMappers, method, bizLocalCache.pre(), bizLocalCache.value());
            if (StrUtil.isNotEmpty(key)) {
                ClearLocalCacheMsg cacheMsg = new ClearLocalCacheMsg(key, bizLocalCache.timeType().name());
                redisHandler.getJedis(0).publish(CLEAR_CHANNEL, JSONObject.toJSONString(cacheMsg));
                clearLocalCache(cacheMsg);
            }
        } else if (annotation instanceof BizRedisCache) {
            BizRedisCache bizLocalCache = (BizRedisCache) annotation;
            String key = getCacheKey(className, arg, fieldMappers, method, bizLocalCache.pre(), bizLocalCache.value());
            if (StrUtil.isNotEmpty(key)) {
                redisHandler.del(key);
            }
        } else if (annotation instanceof BizDoubleCache) {
            BizDoubleCache bizDoubleCache = (BizDoubleCache) annotation;
            String key = getCacheKey(className, arg, fieldMappers, method, bizDoubleCache.pre(), bizDoubleCache.value());
            if (StrUtil.isNotEmpty(key)) {
                redisHandler.del(key);
                ClearLocalCacheMsg cacheMsg = new ClearLocalCacheMsg(key, bizDoubleCache.timeType().name());
                redisHandler.getJedis(0).publish(CLEAR_CHANNEL, JSONObject.toJSONString(cacheMsg));
                clearLocalCache(cacheMsg);
            }
        }

    }

    private String getCacheKey(String methodName, Object arg, List<FieldMapper> fieldMappers, Method method, String pre, String value) throws IllegalAccessException {

        KeyResult result = getKeyResult(methodName, fieldMappers, method, pre, value);
        StringBuilder cacheKey = new StringBuilder(result.cacheKey);
        if (CollectionUtils.isEmpty(result.list)) {
            return "";
        }
        for (FieldMapper mapper : result.list) {
            Field field = mapper.getField();
            Object o = field.get(arg);
            cacheKey.append(":").append(o);
        }
        return cacheKey.toString();

    }

    private KeyResult getKeyResult(String className, List<FieldMapper> fieldMappers, Method method, String pre, String value) {

        String key = className + ":" + method.getName();
        KeyResult result = METHOD_KEY_CACHE.get(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        synchronized (key) {
            result = METHOD_KEY_CACHE.get(key);
            if (Objects.nonNull(result)) {
                return result;
            }
            StringBuilder cacheKey = new StringBuilder(buildClassKey(className, method.getName(), pre, value));
            result = new KeyResult(fieldMappers, cacheKey);
            METHOD_KEY_CACHE.put(key, result);
            return result;
        }

    }

    /**
     * 监听消息删除本地缓存
     */
    @Override
    public void onMessage(String channel, String message) {

        EXECUTOR.execute(() -> {
            try {
                ClearLocalCacheMsg cacheMsg = JSONObject.parseObject(message, ClearLocalCacheMsg.class);
                clearLocalCache(cacheMsg);
            } catch (Exception ignore) {
            }
        });

    }

    private void clearLocalCache(ClearLocalCacheMsg cacheMsg) {
        BizLocalCache.TimeType timeType = BizLocalCache.TimeType.valueOf(cacheMsg.timeType);
        Cache<String, String> localCache = getLocalCache(timeType);
        if (Objects.nonNull(localCache)) {
            localCache.invalidate(cacheMsg.key);
        }
    }

    @Data
    public static class ClearLocalCacheMsg {
        private String key;
        private String timeType;

        public ClearLocalCacheMsg(String key, String timeType) {
            this.key = key;
            this.timeType = timeType;
        }
    }

    @Data
    public static class FieldMapper {
        private Field field;
        private String mapperKey;
        private int sort;
    }

    @Data
    private static class KeyResult {
        public final List<FieldMapper> list;
        public final StringBuilder cacheKey;

        public KeyResult(List<FieldMapper> list, StringBuilder cacheKey) {
            this.list = list;
            this.cacheKey = cacheKey;
        }
    }

    private static class Result {

        public final Method method;
        public final List<FieldMapper> fieldMappers;
        public final Class<? extends Annotation> annotation;

        public Result(Method method, List<FieldMapper> fieldMappers, Class<? extends Annotation> annotation) {
            this.fieldMappers = fieldMappers;
            this.method = method;
            this.annotation = annotation;
        }

    }

}
