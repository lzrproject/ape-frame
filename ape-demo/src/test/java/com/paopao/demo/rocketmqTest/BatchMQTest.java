package com.paopao.demo.rocketmqTest;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 批量消息
 *
 * @Author paoPao
 * @Date 2024/1/17
 */
public class BatchMQTest {

    private static String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void producerTest() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group4");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        List<Message> messages = Arrays.asList(
                new Message("batch_topic", "Hello RocketMQ".getBytes()),
                new Message("batch_topic", "Hello RocketMQ".getBytes()),
                new Message("batch_topic", "Hello RocketMQ".getBytes())
        );
        producer.send(messages, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("消息发送失败" + e);
            }
        });
        System.in.read();
        producer.shutdown();
    }

    @Test
    public void consumerTest() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group4");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.subscribe("batch_topic", "*");
        consumer.start();
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String message = new String(messageExt.getBody());
                System.out.println("消费消息:" + message);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        System.in.read();
        consumer.shutdown();
    }
}
