package com.paopao.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.paopao.demo.domain.Order;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/11
 */
@RestController
@RequestMapping("mq")
public class MqController {

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("orderTest")
    public void test() {
        List<Order> list = Arrays.asList(
                new Order(IdUtil.simpleUUID(), "张三的下订单", 100.0, LocalDateTime.now(), "", 1),
                new Order(IdUtil.simpleUUID(), "张三的发短信", 100.0, LocalDateTime.now(), "", 1),
                new Order(IdUtil.simpleUUID(), "张三的物流", 100.0, LocalDateTime.now(), "", 1),
                new Order(IdUtil.simpleUUID(), "张三的签收", 100.0, LocalDateTime.now(), "", 1),
                new Order(IdUtil.simpleUUID(), "李四的下订单", 100.0, LocalDateTime.now(), "", 2),
                new Order(IdUtil.simpleUUID(), "李四的发短信", 100.0, LocalDateTime.now(), "", 2),
                new Order(IdUtil.simpleUUID(), "李四的物流", 100.0, LocalDateTime.now(), "", 2),
                new Order(IdUtil.simpleUUID(), "李四的签收", 100.0, LocalDateTime.now(), "", 2)
        );
        list.forEach(v -> {
            rocketMQTemplate.syncSendOrderly("bootOrder-topic", JSON.toJSONString(v), String.valueOf(v.getReq()));
        });
        System.out.println("发送消息成功");
    }
}
