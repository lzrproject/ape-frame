package com.paopao.mode.builderPattern;

import lombok.Data;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description
 */
@Data
public class SkuVO {
    private Long skuId;

    private String skuName;

    private String promotionTag;

    private String couponText;
}
