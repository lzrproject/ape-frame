package com.paopao.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paopao.demo.domain.User;
import com.paopao.demo.service.UserService;
import com.paopao.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizerong
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-10-08 17:56:21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    public static final long current = 1;
    public static final long size = 1000;

    @Override
    public Long getTotal() {
        return 100L;
    }

    @Override
    public Page<User> selectListByPage(Long page) {
        Page<User> mysqlPage = new Page<>(page, size);
        Wrapper<User> wrapper = new QueryWrapper<>();
        return this.baseMapper.selectPage(mysqlPage, wrapper);
    }
}




