package com.paopao.user;

import com.paopao.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: paoPao
 * @Date: 2023/3/5
 * @Description: redis测试类
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class RedisUtilsTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void luaTest() {
        String key = "luacas";
        Boolean result = redisUtil.compareAndSet(key, 2L, 3L);
        log.info("RedisUtilTest.luaTest:{}", result);
    }
}
