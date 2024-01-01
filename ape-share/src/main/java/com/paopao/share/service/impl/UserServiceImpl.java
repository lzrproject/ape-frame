package com.paopao.share.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.paopao.share.pojo.User;
import com.paopao.share.mapper.UserMapper;
import com.paopao.share.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
        this.saveOrUpdateBatch()
//        return userMapper.selectCount(null);
    }

    @Override
    public boolean saveBatch(Collection<User> entityList, int batchSize) {
        String sqlStatement = this.getSqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
    }

//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public boolean saveBatch(Collection<T> entityList, int batchSize) {
//        String sqlStatement = this.getSqlStatement(SqlMethod.INSERT_ONE);
//        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
//            sqlSession.insert(sqlStatement, entity);
//        });
//    }
}
