package com.paopao.mode.builderPattern;

import lombok.Data;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description
 */
@Data
public class SkuDO {
    private Long skuId;

    private String skuName;

    private Integer promotionId;

    private Integer couponId;
}
