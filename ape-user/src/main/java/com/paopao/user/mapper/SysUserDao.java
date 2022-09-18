package com.paopao.user.mapper;

import com.paopao.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-17 10:51:48
 */
//@Mapper
public interface SysUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param sysUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("po") SysUser sysUser, @Param("pageNum") Long pageNum, @Param("pageSize") Long pageSize);

    /**
     * 统计总行数
     *
     * @param sysUser 查询条件
     * @return 总行数
     */
    long count(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysUser> entities);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

