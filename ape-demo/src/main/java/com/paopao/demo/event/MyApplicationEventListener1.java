package com.paopao.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/11/27
 */
@Component
public class MyApplicationEventListener1 {

    @EventListener
    public void event(MyApplicationEvent myApplicationEvent) {
        System.out.println("收到消息1:" + myApplicationEvent);
    }
}
