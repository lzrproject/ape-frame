package com.paopao.cache.bizAnnotation;


import com.paopao.cache.util.DateConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis 缓存标记类
 *
 * @author loser
 * @date 2023/06/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizRedisCache {

    /**
     * 缓存统一的前缀
     */
    String pre() default "";

    /**
     * 缓存对应的key
     */
    String value() default "";

    /**
     * 缓存时长 默认 一个小时
     */
    long timeOut() default DateConstant.SECONDS_PER_HOUR;

    /**
     * 缓存清除映射Key
     */
    String mapperKey() default "";

}
