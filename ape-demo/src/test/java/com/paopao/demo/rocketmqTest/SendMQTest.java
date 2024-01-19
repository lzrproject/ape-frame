package com.paopao.demo.rocketmqTest;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;

/**
 * 发送MQ测试
 *
 * @Author paoPao
 * @Date 2024/1/19
 */
@SpringBootTest
public class SendMQTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test() {
        // 同步消息
//        rocketMQTemplate.syncSend();
        // 异步消息
        rocketMQTemplate.asyncSend();
        // 单向消息
        rocketMQTemplate.sendOneWay();

        MessagePostProcessor processor = new MessagePostProcessor() {
            @Override
            public Message<?> postProcessMessage(Message<?> message) {
                message.getHeaders().put("tag", "importance");
                return null;
            }
        };
        // 发送消息
        rocketMQTemplate.convertAndSend("test", "test");

    }

}
