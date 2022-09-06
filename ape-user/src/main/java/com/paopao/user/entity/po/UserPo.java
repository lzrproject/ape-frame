package com.paopao.user.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.paopao.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author 111
 * @Date 2022/8/23 22:22
 * @Description PO 一般跟数据库字段一致
 */
@TableName("user")
@Data
public class UserPo extends BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;


}
