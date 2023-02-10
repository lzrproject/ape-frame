package com.paopao.mode.filterPattern;

import com.paopao.mode.strategyPattern.mid.PayChannelEnum;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description
 */
public enum ArticleFilterEnum {
    WORD_COUNT(0, "数字过滤器");

    private int code;
    private String desc;

    ArticleFilterEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code值获取渠道枚举
     *
     * @param code code
     * @return PayChannelEnum
     */
    public static ArticleFilterEnum getByCode(int code) {
        for (ArticleFilterEnum articleFilterEnum : ArticleFilterEnum.values()) {
            if (articleFilterEnum.code == code) {
                return articleFilterEnum;
            }
        }
        return null;
    }
}
