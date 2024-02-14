package com.powernode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/2/4
 */
@RestController
@RequestMapping()
public class SeckillController {

    @RequestMapping("seckill")
    public String doSeckill() {
        System.out.println("");
        return "111";
    }
}
