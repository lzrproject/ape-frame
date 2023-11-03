package com.paopao.demo.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "userId")
    @Excel(name = "用户ID", width = 18)
    @CsvBindByName(column = "用户ID")
    public Long userid;

    /**
     * 
     */
    @TableField(value = "userName")
    @Excel(name = "用户名称", width = 18)
    @CsvBindByName(column = "用户名称")
    public String username;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}