package com.paopao.user.controller;

import com.paopao.user.entity.User;
import com.paopao.user.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 111
 * @Date 2022/8/10 16:40
 * @Description
 */

@RestController
@RequestMapping("test")
public class TestController {
//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("get")
    public String test(@RequestParam String value){
        System.out.println(ThreadLocalUtil.getCurrentId());

        return "Hello World!";
    }

    @PostMapping("entity")
    public void stringTest(@RequestBody User user){
        System.out.println(user.getPassword());
    }

    @GetMapping("redisTest")
    public void redisTest(){
        redisTemplate.opsForValue().set("name","paopao");
    }
}
