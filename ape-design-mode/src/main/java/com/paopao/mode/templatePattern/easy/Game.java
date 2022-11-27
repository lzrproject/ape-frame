package com.paopao.mode.templatePattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description: 模板类
 */
public abstract class Game {

    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    // 模板
    public final void play() {
        initialize();
        startPlay();
        endPlay();

    }
}
