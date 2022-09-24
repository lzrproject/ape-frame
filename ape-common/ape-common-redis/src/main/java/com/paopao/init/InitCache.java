package com.paopao.init;


import com.paopao.util.SpringContextUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author 111
 * @Date 2022/9/24 16:50
 * @Description 项目启动时调用，可以配合@Order()设置优先级
 */
@Component
public class InitCache implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 我要知道哪些缓存需要进行一个预热
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        // 获取继承 AbstractCache 的所有bean方法
        Map<String, AbstractCache> beanMap = applicationContext.getBeansOfType(AbstractCache.class);

        if(beanMap.isEmpty()){
            return;
        }
        // 调用init方法
        for(Map.Entry<String,AbstractCache> entry : beanMap.entrySet()){
            AbstractCache abstractCache = (AbstractCache) SpringContextUtil.getBean(entry.getValue().getClass());
            abstractCache.initCache();
        }
    }
}
