package com.paopao.mode.strategyPattern.mid;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 策略
 */
public interface PayHandler {
    PayChannelEnum getChannel();

    void dealPay();
}
