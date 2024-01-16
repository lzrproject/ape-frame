package com.paopao.demo.rocketmqTest;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/16
 */
public class OrderMQTest {

    private static String NAME_SRV_ADDR = "localhost:9876";

    public static void main(String[] args) throws Exception {
        producerTest();
    }


    /**
     1^下订单1^100
     2^下订单2^200
     1^物流1^100
     2^物流2^200
     1^签收1^100
     3^下订单3^300
     2^签收2^200
     3^物流3^300
     3^签收3^300
     */
    public static void producerTest() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("MQProduct-Group3");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] strArr = str.split("\\^");
            OrderInfo orderInfo = new OrderInfo();
            String id = "ABCD123" + strArr[0];
            orderInfo.setOrderId(id);
            orderInfo.setOrderName(strArr[1]);
            orderInfo.setOrderPrice(strArr[2]);
            Message message = new Message("order_topic", JSON.toJSONString(orderInfo).getBytes(StandardCharsets.UTF_8));
            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    // 当前主题有多少个队列
                    int size = list.size();
                    // 这个arg就是后面传入的 order.getOrderNumber()
                    String i = (String) arg;
                    int index = i.hashCode() % size;
                    return list.get(index);
                }
            }, id, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功,当前时间:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("消息发送失败");
                }
            });
        }
        System.out.println("1111");
        System.in.read();
        producer.shutdown();
    }

    @Test
    public void consumerTest() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MQProduct-Group3");
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.subscribe("order_topic", "*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                String message = new String(list.get(0).getBody());
                OrderInfo orderInfo = JSON.parseObject(message, OrderInfo.class);
                if ("物流2".equals(orderInfo.getOrderName())) {
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }else {
                    System.out.println("消费消息:" + message);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
        consumer.shutdown();
    }
}

@Data
class OrderInfo {
    private String orderId;
    private String orderName;
    private String orderPrice;
}
