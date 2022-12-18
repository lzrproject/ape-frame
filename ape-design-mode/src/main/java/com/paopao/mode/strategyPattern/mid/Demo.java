package com.paopao.mode.strategyPattern.mid;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.logging.Handler;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 测试
 */
public class Demo {

    @Resource
    private static PayFactory payFactory;
    public static void main(String[] args) {
        PayHandler payHandler = payFactory.getHandlerByCode(1);
        payHandler.dealPay();
    }
}
