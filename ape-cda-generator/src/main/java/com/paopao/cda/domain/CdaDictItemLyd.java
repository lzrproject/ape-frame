package com.paopao.cda.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName CDA_Dict_Item_LYD
 */
@TableName(value ="CDA_Dict_Item_LYD")
@Data
public class CdaDictItemLyd implements Serializable {
    /**
     * 
     */
    @TableId(value = "pk")
    private String pk;

    /**
     * 字典代码
     */
    @TableField(value = "DICT_CODE")
    private String dictCode;

    /**
     * 标准字典项目代码
     */
    @TableField(value = "ITEM_CODE")
    private String itemCode;

    /**
     * 标准字典项目名称
     */
    @TableField(value = "ITEM_VALUE")
    private String itemValue;

    /**
     * 业务系统代码
     */
    @TableField(value = "MATCH_CODE")
    private String matchCode;

    /**
     * 业务系统名称
     */
    @TableField(value = "MATCH_NAME")
    private String matchName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}