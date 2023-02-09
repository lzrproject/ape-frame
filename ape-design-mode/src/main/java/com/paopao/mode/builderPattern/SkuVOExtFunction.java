package com.paopao.mode.builderPattern;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description
 */
public interface SkuVOExtFunction<T extends SkuVO> {

    void buildExtInfo(T skuVO,SkuDO skuDO);
}
