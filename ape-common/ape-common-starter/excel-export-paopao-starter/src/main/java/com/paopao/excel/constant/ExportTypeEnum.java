package com.paopao.excel.constant;

import lombok.Getter;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@Getter
public enum ExportTypeEnum {

    CSV("CSV", "csv文件"),
    EXCEL("excel", "excel文件");

    private final String type;
    private final String name;

    ExportTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
