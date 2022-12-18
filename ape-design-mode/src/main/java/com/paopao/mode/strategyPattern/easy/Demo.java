package com.paopao.mode.strategyPattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 测试
 */
public class Demo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        int res = context.executorStrategy(1, 2);
        System.out.println(res);
    }
}
