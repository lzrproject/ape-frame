package com.paopao.demo.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;

/**
 * 消费者监听器
 *
 * @Author paoPao
 * @Date 2024/1/22
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "boot-topic",
        consumerGroup = "boot-group1",
        consumeMode = ConsumeMode.CONCURRENTLY
//        messageModel = MessageModel.CLUSTERING
)
public class SimpleMsgListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("{},消费了一条消息:{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), message);
    }

}
