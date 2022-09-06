package com.paopao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paopao.user.entity.User;
import com.paopao.user.entity.dto.UserDto;
import com.paopao.user.entity.po.UserPo;

/**
 * @Author 111
 * @Date 2022/8/24 17:44
 * @Description
 */
public interface UserService extends IService<UserPo> {

    int addUser(UserDto userDto);
}
