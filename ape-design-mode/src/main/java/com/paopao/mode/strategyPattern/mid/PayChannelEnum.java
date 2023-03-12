package com.paopao.mode.strategyPattern.mid;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 支付枚举
 */
@Getter
public enum PayChannelEnum {

    ZFB_PAY(0, "支付宝支付"),
    WX_PAY(1, "微信支付"),
    BANK_PAY(2, "银行卡支付");

    private int code;

    private String desc;

    PayChannelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 枚举优化
     */
    public static final Map<Integer,PayChannelEnum> clientChannelMap = Stream.of(PayChannelEnum.values())
            .collect(Collectors.toMap(PayChannelEnum::getCode,payChannelEnum -> payChannelEnum));

    /**
     * 根据code值获取渠道枚举
     * @param code code
     * @return PayChannelEnum
     */
    public static PayChannelEnum getByCode(int code) {
        for (PayChannelEnum payChannelEnum:PayChannelEnum.values()) {
            return payChannelEnum;
        }
        return null;
    }
}
