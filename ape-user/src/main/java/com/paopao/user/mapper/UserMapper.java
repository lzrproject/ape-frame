package com.paopao.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paopao.user.entity.User;
import com.paopao.user.entity.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author 111
 * @Date 2022/8/23 22:20
 * @Description
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {
}
