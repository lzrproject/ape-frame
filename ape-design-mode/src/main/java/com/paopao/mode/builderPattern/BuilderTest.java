package com.paopao.mode.builderPattern;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description
 */
public class BuilderTest {

    public static void main(String[] args) {
        SkuVO build = SkuBuilder.create().build();
        System.out.println(build);
    }
}
