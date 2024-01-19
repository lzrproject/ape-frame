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

import java.util.List;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/17
 */
public class DeadMQTest {

    private static String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void producerTest() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group4");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        Message message = new Message("dead_topic", "我是一条死信消息".getBytes());
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("消息发送失败");
            }
        });
        System.in.read();
        producer.shutdown();
    }

    @Test
    public void consumerTest() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group4");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.setMaxReconsumeTimes(2);
        consumer.subscribe("dead_topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                System.out.println("第" + messageExt.getReconsumeTimes() + "次重试,消息:" + new String(messageExt.getBody()));
                // 超过2次重试后,消息进入死信队列
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
        System.in.read();
        consumer.shutdown();
    }
}
