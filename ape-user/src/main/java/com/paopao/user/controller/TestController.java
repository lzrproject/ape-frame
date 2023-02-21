package com.paopao.user.controller;

import com.paopao.tool.freemarker.ExportWordUtil;
import com.paopao.tool.timeout.TimeRecord;
import com.paopao.user.entity.User;
import com.paopao.user.utils.ThreadLocalUtil;
import com.paopao.util.CacheUtil;
import com.paopao.util.RedisShareLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author 111
 * @Date 2022/8/10 16:40
 * @Description 测试类
 */

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisShareLockUtil redisShareLockUtil;

    @Autowired
    private CacheUtil cacheUtil;

    @GetMapping("get")
    @TimeRecord
    public String test(@RequestParam String value) {
        System.out.println(ThreadLocalUtil.getCurrentId());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World!";
    }

    @PostMapping("entity")
    public void stringTest(@RequestBody User user) {
        System.out.println(user.getPassword());
    }

    @GetMapping("redisTest")
    public void redisTest() {
        redisTemplate.opsForValue().set("name", "paopao");
    }

    @GetMapping("redisLock")
    public void redisLockDemo() {
        String requestId = "123214";
        boolean lockTest = redisShareLockUtil.lock("lockTest", requestId, 10000L);
        System.out.println(lockTest);
    }

    @GetMapping("testLog")
    public void testLog() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            log.info("这是第{}条日志", i);
        }
        long end = System.currentTimeMillis();
        log.info("耗时{}", end - start);
    }

    @GetMapping("testExport")
    public void testExport() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 21);
        map.put("body", "打篮球");
        map.put("signName", "王五");
        ExportWordUtil.exportWord(map, "个人资料", "aaa.ftl");
    }

    /**
     * guava本地缓存
     */
    @GetMapping("/localCache")
    public void localCacheTest() {
        List<Long> skuIdList = new ArrayList<>();
        String cachePrefix = "skuInfo.skuName";
        Map<Long,SkuInfo> result = cacheUtil.getResult(skuIdList, cachePrefix, SkuInfo.class, (list) -> {
            return getSkuInfo();
        });
        System.out.println(result);
    }

    public Map<Long,SkuInfo> getSkuInfo() {
        return Collections.emptyMap();
    }

    static class SkuInfo {
        private Long id;
        private String name;
        private Long price;
    }
}
