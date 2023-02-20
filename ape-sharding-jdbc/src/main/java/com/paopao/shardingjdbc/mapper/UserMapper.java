package com.paopao.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paopao.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: paoPao
 * @Date: 2023/2/18
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
