package com.paopao.cache.bizAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 双层缓存标记类
 *
 * @author loser
 * @date 2023/06/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperKey {

    MapperFiled[] value();

}
