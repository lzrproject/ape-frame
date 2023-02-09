package com.paopao.share.service.impl;

import com.paopao.share.pojo.User;
import com.paopao.share.mapper.UserMapper;
import com.paopao.share.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author erabbit_admin_111
 * @since 2022-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Long getCount() {
        return userMapper.selectCount(null);
    }
}
