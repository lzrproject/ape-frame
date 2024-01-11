package com.paopao.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/11
 */
@RestController
public class MqController {

    @RequestMapping("")
    public void test(){
        System.out.println("test");
    }
}
