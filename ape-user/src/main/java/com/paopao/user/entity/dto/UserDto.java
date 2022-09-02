package com.paopao.user.entity.dto;

import lombok.Data;

/**
 * @Author 111
 * @Date 2022/8/24 17:45
 * @Description DTO 一般处理业务字段，数据传输对象！
 */

@Data
public class UserDto {

    private Long id;

    private String name;

    private Integer age;
}
