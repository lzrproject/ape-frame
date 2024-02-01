package com.paopao.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

/**
 * 基于 Redis 的延迟队列
 *
 * @Author paoPao
 * @Date 2024/2/1
 */
@Slf4j
public class RedisDelayingQueue<T> {
    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    private final Type TaskType = new TypeReference<TaskItem<T>>() {
    }.getType();
    private Jedis jedis;
    private String queueKey;

    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    /**
     * 发送消息
     *
     * @param msg       消息
     * @param delayTime 过期时间 (s)
     */
    public void delay(T msg, long delayTime) {
        TaskItem<T> taskItem = new TaskItem<>();
        taskItem.id = UUID.randomUUID().toString();
        taskItem.msg = msg;
        String s = JSON.toJSONString(taskItem);
        jedis.zadd(queueKey, System.currentTimeMillis() + (delayTime * 1000), s);
        System.out.println("msg:" + msg + ",delayTime:" + delayTime
                + ",sendTime:" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
//        log.info("msg:{},delayTime:{},sendTime:{}", msg, delayTime, System.currentTimeMillis() / 1000);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            // 只取一条
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            System.out.println("轮询中...." + values);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            String next = values.iterator().next();
            // 抢到了
            if (jedis.zrem(queueKey, next) > 0) {
                TaskItem<T> taskItem = JSON.parseObject(next, TaskType);
                this.handleMsg(taskItem.msg);
            }
        }
    }

    private void handleMsg(T msg) {
        System.out.println("msg:" + msg
                + ",costTime:" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
//        log.info("msg:{},costTime:{}", msg, System.currentTimeMillis() / 1000);
//        System.out.println(msg);
    }
}
