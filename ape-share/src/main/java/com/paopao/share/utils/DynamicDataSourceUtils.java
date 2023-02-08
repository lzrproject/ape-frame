package com.paopao.share.utils;

import com.paopao.share.config.dynamicDataSourceConfig.DatabaseContextHolder;
import com.paopao.share.config.dynamicDataSourceConfig.DatabaseType;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description 封装数据源选择
 */
public class DynamicDataSourceUtils {
    public static void chooseBasicDataSource(){
        DatabaseContextHolder.setDatabaseType(DatabaseType.test2);
    }

    public static void chooseBranchDataSource(){
        DatabaseContextHolder.setDatabaseType(DatabaseType.test3);
    }
}
