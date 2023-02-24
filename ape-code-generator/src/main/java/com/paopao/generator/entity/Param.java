package com.paopao.generator.entity;

import lombok.Data;

@Data
public class Param {
    private String modelName;
    private String tableName;
    private String tablePrefix;
    private String url;
    private String driverClass;
    private String username;
    private String password;
}
