package com.paopao.mode.strategyPattern.mid;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 支付工厂类
 */
//@Component
public class PayFactory implements InitializingBean {

    @Resource
    List<PayHandler> payHandlers;

    private Map<PayChannelEnum, PayHandler> handlerMap = new HashMap<>();

    public PayHandler getHandlerByCode(int code) {
        PayChannelEnum channelEnum = PayChannelEnum.getByCode(code);
        return handlerMap.get(channelEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (PayHandler payHandler : payHandlers) {
            handlerMap.put(payHandler.getChannel(), payHandler);
        }
    }
}
