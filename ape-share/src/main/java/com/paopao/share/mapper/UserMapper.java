package com.paopao.share.mapper;

import com.paopao.share.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author erabbit_admin_111
 * @since 2022-08-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select count(*) from t_user")
    int getCount();

}
