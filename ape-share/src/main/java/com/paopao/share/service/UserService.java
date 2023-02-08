package com.paopao.share.service;

import com.paopao.share.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author erabbit_admin_111
 * @since 2022-08-30
 */
public interface UserService extends IService<User> {
    int getCount();
}
