package com.paopao.share.config.dynamicDataSourceConfig;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description 定义一个线程安全的数据源容器
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }
}
