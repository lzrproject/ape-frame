package com.paopao.demo.rocketmqTest;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 异步消息
 *
 * @Author paoPao
 * @Date 2024/1/11
 */
public class AsyncMQTest {

    private String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void test() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group1");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        Message message = new Message("message1", "异步消息".getBytes(StandardCharsets.UTF_8));
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送异常");
            }
        });
        System.out.println("看看谁先执行！");
        // 挂起JVM
        System.in.read();
        producer.shutdown();
    }

    @Test
    public void consumerMQ() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group1");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        // 订阅一个主题来消费   *表示没有过滤参数 表示这个主题的任何消息
        consumer.subscribe("message1", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(list.size());
                String message = new String(list.get(0).getBody());
                System.out.println("当前线程:" + Thread.currentThread().getName() + ", 消息:" + message);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
