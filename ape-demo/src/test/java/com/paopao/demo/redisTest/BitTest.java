package com.paopao.demo.redisTest;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/2/1
 */
public class BitTest {

    /**
     * Bit
     */
    @Test
    public void test1() {
        Jedis jedis = new Jedis();
        String key = "bit_test";
        String value = "Hello world";
        jedis.setbit(key.getBytes(StandardCharsets.UTF_8), 0, value.getBytes());
        jedis.close();
    }

    /**
     * HyperLogLog
     */
    @Test
    public void test2() {
        Jedis jedis = new Jedis();
        for (int i = 0; i < 100000; i++) {
            jedis.pfadd("codehole", "user" + i);
        }
        long codehole = jedis.pfcount("codehole");
        System.out.printf("%d %d\n", 100000, codehole);
        jedis.close();
    }

    /**
     * 布隆过滤器
     */
    @Test
    public void test3() {
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8)
                , 100, 0.01);
        filter.put("hello1");
        filter.put("hello2");
        filter.put("hello3");
        System.out.println(filter.mightContain("hello1"));
        System.out.println(filter.mightContain("hello4"));
    }
}
