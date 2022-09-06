package com.paopao.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paopao.user.entity.User;
import com.paopao.user.entity.dto.UserDto;
import com.paopao.user.entity.po.UserPo;
import com.paopao.user.mapper.UserMapper;
import com.paopao.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 111
 * @Date 2022/8/24 17:50
 * @Description
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(UserDto userDto) {
        UserPo userPo = new UserPo();
        // 效率不高
        BeanUtils.copyProperties(userDto,userPo);
        userPo.setCreateBy("我改了");
        int count = userMapper.insert(userPo);

        return count;
    }
}
