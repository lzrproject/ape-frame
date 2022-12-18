package com.paopao.mode.strategyPattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/12/18
 * @Description: 乘法
 */
public class OperationMultiply implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1*num2;
    }
}
