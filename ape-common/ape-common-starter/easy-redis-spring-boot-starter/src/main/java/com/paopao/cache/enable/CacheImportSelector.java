package com.paopao.cache.enable;

import com.paopao.cache.config.CacheConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 通过注解开启缓存功能
 *
 * @author loser
 * @date 2023/06/20
 */
public class CacheImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        return new String[]{CacheConfiguration.class.getName()};
    }

}