package com.paopao.cache.bizAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存动态参数字段映射标记
 *
 * @author loser
 * @date 2023/06/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperFiled {

    String key();

    int sort();

}
