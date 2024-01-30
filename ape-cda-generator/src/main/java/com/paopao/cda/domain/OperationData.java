package com.paopao.cda.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/25
 */
@Data
public class OperationData {

    @ExcelProperty(index = 0)
    private String itemCode1;

    @ExcelProperty(index = 1)
    private String itemCode2;

    @ExcelProperty(index = 2)
    private String itemName;
}
