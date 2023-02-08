package com.paopao.share.config.dynamicDataSourceConfig;

import com.paopao.share.config.dynamicDataSourceConfig.DatabaseContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description 定义动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDatabaseType();
    }
}
