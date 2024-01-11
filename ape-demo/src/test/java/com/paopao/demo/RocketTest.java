package com.paopao.demo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/11
 */
public class RocketTest {

    private String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void test() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group1");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        Message message = new Message("message1", "日志".getBytes(StandardCharsets.UTF_8));
        producer.sendOneway(message);
        System.out.println("成功");
        producer.shutdown();

    }
}
