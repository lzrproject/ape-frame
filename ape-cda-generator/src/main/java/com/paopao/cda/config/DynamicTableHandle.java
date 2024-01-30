package com.paopao.cda.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
public class DynamicTableHandle implements TableNameHandler {

    public static final ThreadLocal<String> TABLE_CONDITION = new ThreadLocal<String>();

    public DynamicTableHandle() {
    }

//    public DynamicTableHandle(String tableName) {
//        TABLE_CONDITION.set(tableName);
//    }

    public static void setThreadLocal(String tableName) {
        TABLE_CONDITION.set(tableName);
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        return TABLE_CONDITION.get();
    }
}
