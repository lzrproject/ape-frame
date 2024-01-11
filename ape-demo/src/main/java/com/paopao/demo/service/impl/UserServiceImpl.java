package com.paopao.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paopao.demo.domain.User;
import com.paopao.demo.service.UserService;
import com.paopao.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lizerong
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-01-10 14:54:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




