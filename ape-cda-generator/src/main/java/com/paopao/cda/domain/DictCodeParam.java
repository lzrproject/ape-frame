package com.paopao.cda.domain;

import lombok.Data;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
@Data
public class DictCodeParam {
    private String dictCode;
    private String[] fieldArr;
    private String tableName;
    private String[] groupByParam;
}
