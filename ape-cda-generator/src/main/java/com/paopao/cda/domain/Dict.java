package com.paopao.cda.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/31
 */
@Data
public class Dict {

    @TableField("DICT_CODE")
    @ExcelIgnore
    private String dictCode;

    @TableField("ITEM_CODE")
    @ExcelIgnore
    private String itemCode;

    @TableField("ITEM_VALUE")
    @ExcelIgnore
    private String itemValue;

    @TableField("MATCH_CODE")
    @ExcelProperty(index = 4)
    private String matchCode;

    @TableField("MATCH_NAME")
    @ExcelProperty(index = 5)
    private String matchName;
}
