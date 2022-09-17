package com.paopao.user.convert;


import com.paopao.user.entity.SysUser;
import com.paopao.user.entity.req.SysUserReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author 111
 * @Date 2022/9/17 20:11
 * @Description 映射器接口 用于生成类型安全的bean映射类
 */
@Mapper
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    SysUser convertReqToSysUser(SysUserReq sysUserReq);
}
