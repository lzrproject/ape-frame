package com.paopao.mode.strategyPattern.mid.handler;

import com.paopao.mode.strategyPattern.mid.PayChannelEnum;
import com.paopao.mode.strategyPattern.mid.PayHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 支付宝支付
 */
@Component
public class ZfbPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.ZFB_PAY;
    }

    @Override
    public void dealPay() {
        System.out.println("支付宝支付");
    }
}
