package com.paopao.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paopao.shardingjdbc.entity.Order;
import com.paopao.shardingjdbc.entity.User;
import com.paopao.shardingjdbc.mapper.OrderMapper;
import com.paopao.shardingjdbc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: paoPao
 * @Date: 2023/2/18
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 读写分离
     */
    @Test
    public void test() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    /**
     * 水平分片：插入数据测试
     */
    @Test
    public void testInsertOrder(){
        for (long i = 1; i < 5; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }

        for (long i = 5; i < 9; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    /**
     * 水平分片：查询所有记录
     * 查询了两个数据源，每个数据源中使用UNION ALL连接两个表
     */
    @Test
    public void testShardingSelectAll(){

        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }

    /**
     * 水平分片：根据user_id查询记录
     * 查询了一个数据源，每个数据源中使用UNION ALL连接两个表
     */
    @Test
    public void testShardingSelectByUserId(){

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", 1L);
        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        orders.forEach(System.out::println);
    }

    /**
     * 水平分片：插入数据测试
     */
    @Test
    public void testInsertSnowFlake(){
        Order order = new Order();
        order.setOrderNo("ATGUIGU111");
        order.setUserId(1L);
        order.setAmount(new BigDecimal(101));
        orderMapper.insert(order);
    }
}
