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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * tag标签
 *
 * @Author paoPao
 * @Date 2024/1/17
 */
public class TagMQTest {

    private static String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void producerTest() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group4");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        List<Message> messages = Arrays.asList(
                new Message("tag_topic", "tagB", "我是一个带标记的消息1".getBytes(StandardCharsets.UTF_8)),
                new Message("tag_topic", "tagB", "我是一个带标记的消息2".getBytes(StandardCharsets.UTF_8)),
                new Message("tag_topic", "tagB", "我是一个带标记的消息3".getBytes(StandardCharsets.UTF_8)),
                new Message("tag_topic", "tagB", "我是一个带标记的消息4".getBytes(StandardCharsets.UTF_8))
        );
//        Message message = new Message("tag_topic", "tagB", "我是一个带标记的消息".getBytes(StandardCharsets.UTF_8));
        for (Message message : messages) {
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
        }

        System.in.read();
        producer.shutdown();
    }

    @Test
    public void consumerTest1() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group4");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.subscribe("tag_topic", "tagA || tagB");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String message = new String(messageExt.getBody());
                System.out.println("消费者1,消费消息：" + message);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
        consumer.shutdown();
    }

    @Test
    public void consumerTest2() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group4");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.subscribe("tag_topic", "tagB || tagC");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String message = new String(messageExt.getBody());
                System.out.println("消费者2,消费消息：" + message);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
        consumer.shutdown();
    }
}
