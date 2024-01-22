package com.paopao.demo.rocketmq;

import com.alibaba.fastjson.JSON;
import com.paopao.demo.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消费者
 *
 * @Author paoPao
 * @Date 2024/1/22
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "bootOrder-topic",
        consumerGroup = "boot-group",
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING
)
public class ObjMsgListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // TODO 某个消息异常了，如何处理。
        Order order = JSON.parseObject(message, Order.class);
        log.info("当前消费的消息:{}", order.getOrderName());
    }
}
