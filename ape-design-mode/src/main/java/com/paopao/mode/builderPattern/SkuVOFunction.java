package com.paopao.mode.builderPattern;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description
 */
public interface SkuVOFunction<T extends SkuVO> {

    T newInstance();
}
