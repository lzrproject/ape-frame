package com.paopao.demo.controller;

import com.paopao.demo.event.MyApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/11/27
 */
@RestController
@RequestMapping("event")
public class ApplicationEventController {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("push")
    public void pushEvent() {
        MyApplicationEvent event = new MyApplicationEvent(this, "泡泡", 20);
        applicationContext.publishEvent(event);
    }

    @RequestMapping("push1")
    public void pushEvent1() {
//        MyApplicationEvent event = new MyApplicationEvent(this, "泡泡", 20);
        applicationContext.publishEvent("泡泡");
    }
}
