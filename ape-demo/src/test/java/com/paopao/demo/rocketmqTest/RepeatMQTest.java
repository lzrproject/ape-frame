package com.paopao.demo.rocketmqTest;

import cn.hutool.core.util.IdUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/18
 */
public class RepeatMQTest {

    private static String NAME_SRV_ADDR = "localhost:9876";

    @Test
    public void producerTest() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group4");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();

        Message message1 = new Message("repeat_topic", "重复消费1".getBytes(StandardCharsets.UTF_8));
        Message message2 = new Message("repeat_topic", "重复消费2".getBytes(StandardCharsets.UTF_8));
        String uuid = IdUtil.fastSimpleUUID();
        message1.setKeys(uuid);
        message2.setKeys(uuid);
        List<Message> messages = Arrays.asList(message1, message2);
        producer.send(messages, new SendCallback() {
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

    private List<String> list = new ArrayList<>();

    @Test
    public void consumerTest() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group4");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.subscribe("repeat_topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String msg = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                String keys = messageExt.getKeys();
                System.out.println(msg + "  "+ keys);
                synchronized (list) {
                    if (list.contains(keys)) {
                        System.out.println("消息重复消费");
                    } else {
                        list.add(keys);
                    }
                }
                System.out.println(list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
        consumer.shutdown();
    }
}
