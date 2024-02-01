package com.paopao.demo.redisTest;

import com.paopao.demo.domain.User;
import com.paopao.util.RedisDelayingQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * 基于 Redis 延迟队列测试
 *
 * @Author paoPao
 * @Date 2024/2/1
 */
//@SpringBootTest(classes = DemoApplication.class)
public class DelayQueue {

//    @Autowired
//    private Jedis jedis;

    @Test
    public void delayQueueTest() throws Exception {
        Jedis jedis = new Jedis();
        Set<String> values = jedis.zrangeByScore("delayQueue", 0, System.currentTimeMillis(), 0, 1);
        System.out.println(jedis.zrem("delayQueue", values.iterator().next()));
//        System.out.println(values);
//        this.addMsg(jedis);
//        Thread.sleep(2000);
    }

    private void addMsg(Jedis jedis) {
        RedisDelayingQueue<User> queue = new RedisDelayingQueue<User>(jedis, "delayQueue");
        User user = new User();
        user.setUserid(300L);
        user.setUsername("王五");
        queue.delay(user, 20);
    }

    @Test
    public void loopQueueTest() {
        Jedis jedis = new Jedis();
        this.genMsg(jedis);
    }

    @Test
    public void loopQueueTest1() {
        Jedis jedis = new Jedis();
        this.genMsg(jedis);
    }

    @Test
    public void loopQueueTest2() {
        Jedis jedis = new Jedis();
        this.genMsg(jedis);
    }


    private void genMsg(Jedis jedis) {
        RedisDelayingQueue<User> queue = new RedisDelayingQueue<User>(jedis, "delayQueue");
        queue.loop();
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        Date date1 = new Date(now);
        Date date2 = new Date(now + (8 * 1000));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
        String dateTime1 = sdf.format(date1);
        String dateTime2 = sdf.format(date2);
        System.out.println(dateTime1 + "  " + dateTime2);
    }
}
