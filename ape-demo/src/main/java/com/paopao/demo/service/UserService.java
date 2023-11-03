package com.paopao.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paopao.demo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lizerong
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-08 17:56:21
*/
public interface UserService extends IService<User> {

    public Long getTotal();

    public Page<User> selectListByPage(Long page);
}
