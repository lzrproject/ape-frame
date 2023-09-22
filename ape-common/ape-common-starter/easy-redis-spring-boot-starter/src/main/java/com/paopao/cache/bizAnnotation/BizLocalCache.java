package com.paopao.cache.bizAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本地缓存标记类
 *
 * @author loser
 * @date 2023/06/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizLocalCache {

    /**
     * 缓存统一的前缀
     */
    String pre() default "";

    /**
     * 缓存对应的key
     */
    String value() default "";

    /**
     * 默认缓存时长为一分钟
     */
    TimeType timeType() default TimeType.MIN_1;

    /**
     * 缓存清除映射Key
     */
    String mapperKey() default "";

    /**
     * 时间类型
     */
    enum TimeType {
        SECOND_3,
        SECOND_10,
        MIN_1,
        MIN_5,
        MIN_10,
        HOUR_1,
    }

}
