package com.paopao.user.entity.req;

import com.paopao.request.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2022-09-17 10:49:37
 */
@Data
public class SysUserReq extends PageRequest implements Serializable {
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

