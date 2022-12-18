package com.paopao.mode.strategyPattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description:
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executorStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
