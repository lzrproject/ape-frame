package com.paopao.mode.templatePattern.prod;

import com.paopao.response.Result;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description:
 */
public class ApiTemplateDemo {

    public static void main(String[] args) {

        new ApiTemplate().execute(new Action() {
            @Override
            public void validate() {
                System.out.println("开始校验");
            }

            @Override
            public void execute() {
                System.out.println("执行");
            }

            @Override
            public void after() {
                System.out.println("后续执行");
            }
        });
    }
}
