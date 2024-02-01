package com.paopao.demo.redisTest;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/2/1
 */
public class BitTest {

    @Test
    public void test1() {
        Jedis jedis = new Jedis();
        String key = "bit_test";
        String value = "Hello world";
        for (char c : value.toCharArray()) {
            jedis.setbit(key, 0, c)
        }
    }
}
