package com.paopao.mode.strategyPattern.mid.handler;

import com.paopao.mode.strategyPattern.mid.PayChannelEnum;
import com.paopao.mode.strategyPattern.mid.PayHandler;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 微信支付
 */
public class WxPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WX_PAY;
    }

    @Override
    public void dealPay() {
        System.out.println("微信支付");
    }
}
