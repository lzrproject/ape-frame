package com.paopao.user.init;

import com.alibaba.fastjson.JSON;
import com.paopao.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description 项目启动初始化
 */
@Component
@Slf4j
public class ApplicationInit implements ApplicationListener<ApplicationReadyEvent> {
    Map<String, InitFunction> initFunctionMap = new HashMap<>();

    {
        initFunctionMap.put("预热fastjson", this::initFastJson);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("服务启动");
        // 预热httpClient
        // 预热Json
        initFunctionMap.forEach((k, v) -> {
            try {
                long start = System.currentTimeMillis();
                v.invoke();
                long end = System.currentTimeMillis();
                log.info("ApplicationInit预热时间：{}", end - start);
            } catch (Exception e) {
                log.info("ApplicationInit：{} error：{}", k, e);
            }
        });
        // 预热一个线程池
    }

    private void initFastJson() {
        User user = new User();
        user.setUsername("张三");
        user.setPassword("1243");
        String s = JSON.toJSONString(user);
        System.out.println(s);
    }

    interface InitFunction {
        void invoke();
    }
}
