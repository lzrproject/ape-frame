package com.paopao.user.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2022-09-17 10:49:37
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 929606677376877751L;
    
    private Long id;
    
    private String name;
    
    private Integer age;
    
    private String createBy;
    
    private Date createTime;
    
    private String updateBy;
    
    private Date updateTime;
    
    private Integer deleteFlag;
    
    private Integer version;


}

