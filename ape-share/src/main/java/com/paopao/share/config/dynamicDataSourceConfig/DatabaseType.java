package com.paopao.share.config.dynamicDataSourceConfig;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description 枚举类管理数据源
 */
public enum DatabaseType {
    // 数据源
    test1("test1", "test1"),
    test2("test2", "test2"),
    test3("test3","test3");

    private String name;
    private String value;

    DatabaseType(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
}
