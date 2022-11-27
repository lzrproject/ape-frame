package com.paopao.mode.templatePattern.easy;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description:
 */
public class TemplatePatternDemo {
    public static void main(String[] args) {
        Game football = new Football();
        football.play();
        Game basketball = new Basketball();
        basketball.play();
    }
}
