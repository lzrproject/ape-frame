package com.paopao.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
    private Long userid;

    /**
     * 
     */
    @TableField(value = "userName")
    private String username;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}