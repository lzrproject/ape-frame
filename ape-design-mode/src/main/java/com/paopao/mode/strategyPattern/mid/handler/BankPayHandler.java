package com.paopao.mode.strategyPattern.mid.handler;

import com.paopao.mode.strategyPattern.mid.PayChannelEnum;
import com.paopao.mode.strategyPattern.mid.PayHandler;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 银行卡逻辑
 */
public class BankPayHandler implements PayHandler {
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.BANK_PAY;
    }

    @Override
    public void dealPay() {
        System.out.println("银行卡支付");
    }
}
