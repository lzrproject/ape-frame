package com.paopao.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paopao.shardingjdbc.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: paoPao
 * @Date: 2023/2/19
 * @Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT  id,order_no,user_id,amount  FROM t_order order by id")
    public List<Order> selectSort();
}
