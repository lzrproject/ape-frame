package com.paopao.cda.domain;

import lombok.Data;

import java.util.Map;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/29
 */
@Data
public class SimilarParam<T> {

    private T item;
    private Map<CharSequence, Integer> similarVal;
}
