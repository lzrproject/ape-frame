package com.paopao.user.service.impl;

import com.paopao.request.PageResponse;
import com.paopao.user.convert.SysUserConverter;
import com.paopao.user.entity.SysUser;
import com.paopao.user.mapper.SysUserDao;
import com.paopao.user.entity.req.SysUserReq;
import com.paopao.user.service.SysUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-09-17 10:49:57
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @Cacheable(cacheNames = "SysUser", key = "'querySysUserById'+#id")
    public SysUser queryById(Long id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param sysUserReq 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResponse<SysUser> queryByPage(SysUserReq sysUserReq) {
//        SysUser sysUser = new SysUser();
//        BeanUtils.copyProperties(sysUserReq,sysUser);
        SysUser sysUser = SysUserConverter.INSTANCE.convertReqToSysUser(sysUserReq);

        PageResponse<SysUser> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(sysUserReq.getPageNo());
        pageResponse.setPageSize(sysUserReq.getPageSize());
        long pageNum = (sysUserReq.getPageNo() - 1) * sysUserReq.getPageSize();
        long total = this.sysUserDao.count(sysUser);
        pageResponse.setTotal(total);
        List<SysUser> sysUsers = this.sysUserDao.queryAllByLimit(sysUser, pageNum, sysUserReq.getPageSize());
        pageResponse.setRecords(sysUsers);
        return pageResponse;
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysUserDao.deleteById(id) > 0;
    }
}
