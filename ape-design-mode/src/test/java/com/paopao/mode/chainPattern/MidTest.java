package com.paopao.mode.chainPattern;

import com.alibaba.fastjson.JSON;
import com.paopao.mode.ModeApplication;
import com.paopao.mode.chainPattern.mid.AbstractCheckHandler;
import com.paopao.mode.chainPattern.mid.CheckFactory;
import com.paopao.mode.chainPattern.mid.ProductCheckHandlerConfig;
import com.paopao.mode.strategyPattern.mid.PayHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: paoPao
 * @Date: 2023/7/26
 * @Description: 责任链模式测试 MID
 */
@SpringBootTest(classes = ModeApplication.class)
@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = "com.paopao.mode.chainPattern.mid")
@Slf4j
public class MidTest {

    @Autowired
    private CheckFactory checkFactory;

    @Test
    public void test() {
        ProductCheckHandlerConfig handlerConfig = this.getHandlerConfig();
        AbstractCheckHandler handler = checkFactory.getHandler(handlerConfig);
        // 执行
        String result = executeChain(handler, "1");
        log.info("The chain success ");
    }

    /**
     * 执行
     * @param handler
     * @param param
     * @return
     */
    private String executeChain(AbstractCheckHandler handler, String param) {
        return handler.handle(param);
    }

    /**
     * 读取配置中心参数
     * @return ProductCheckHandlerConfig
     */
    private ProductCheckHandlerConfig getHandlerConfig() {
        // 可以放在配置中心
        String configJson = "{\"handler\":\"nullValueCheckHandler\",\"down\":true," +
                "\"next\":{\"handler\":\"validValueCheckHandler\",\"next\":null}}";
        return JSON.parseObject(configJson, ProductCheckHandlerConfig.class);
    }

    @Resource
    private List<PayHandler> bankPayHandler1;

    @Test
    public void beanTest() {
        BeanUtils.copyProperties();
        System.out.println(bankPayHandler1);
    }

}
