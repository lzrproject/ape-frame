package com.paopao.demo.rocketmqTest;

import com.paopao.demo.DemoApplication;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/22
 */
@SpringBootTest(classes = {DemoApplication.class})
public class BootMQTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test() throws Exception {
        // 同步消息
//        rocketMQTemplate.syncSend();
        // 异步消息
//        rocketMQTemplate.asyncSend();
        // 单向消息
//        rocketMQTemplate.sendOneWay();

//        MessagePostProcessor processor = new MessagePostProcessor() {
//            @Override
//            public Message<?> postProcessMessage(Message<?> message) {
//                message.getHeaders().put("tag", "importance");
//                return null;
//            }
//        };
//        rocketMQTemplate.syncSend("", "", 2000, 4);
        Message<String> message = MessageBuilder
                .withPayload("发送第一条消息")
                .build();
        // 发送消息
        SendResult sendResult = rocketMQTemplate.syncSend("boot-topic", message, 5000, 2);
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " 发送了一条消息,"
                + sendResult.getSendStatus());
        System.in.read();
    }
}
