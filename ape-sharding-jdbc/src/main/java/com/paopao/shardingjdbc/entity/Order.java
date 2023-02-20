package com.paopao.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: paoPao
 * @Date: 2023/2/19
 * @Description:
 */
@TableName("t_order")//逻辑表名
@Data
public class Order {
    //当配置了shardingsphere-jdbc的分布式序列时，自动使用shardingsphere-jdbc的分布式序列
    //当没有配置shardingsphere-jdbc的分布式序列时，自动依赖数据库的主键自增策略
    @TableId(type = IdType.AUTO)
//    @TableId(type = IdType.ASSIGN_ID)//分布式id（默认值）与shardingSphere 雪花ID 二选一
    private Long id;
    private String orderNo;//自动映射数据库表中的order_no字段
    private Long userId;//user_id
    private BigDecimal amount;
}
