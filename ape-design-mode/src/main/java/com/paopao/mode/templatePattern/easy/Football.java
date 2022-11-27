package com.paopao.mode.templatePattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description:
 */
public class Football extends Game{
    @Override
    void initialize() {
        System.out.println("准备踢足球");
    }

    @Override
    void startPlay() {
        System.out.println("开始踢足球");
    }

    @Override
    void endPlay() {
        System.out.println("结束踢足球");
    }
}
