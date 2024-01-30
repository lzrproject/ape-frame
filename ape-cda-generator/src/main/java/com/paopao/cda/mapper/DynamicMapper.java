package com.paopao.cda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paopao.cda.domain.ResponseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
@Mapper
public interface DynamicMapper {

//    @Select("${sqlStr}")
    public List<ResponseData> selectByCondition(String sqlStr);
}
