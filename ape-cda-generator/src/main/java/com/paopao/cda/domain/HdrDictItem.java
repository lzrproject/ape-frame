package com.paopao.cda.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/23
 */
@Data
@TableName("\"HDR_DICT_ITEM\"")
public class HdrDictItem extends Dict {

    @TableField("PK")
    @ExcelIgnore
    private String pk;


}
