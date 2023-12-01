package com.paopao.demo.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 事件监听
 *
 * @Author paoPao
 * @Date 2023/11/27
 */
@Component
public class MyApplicationEventListener implements ApplicationListener<MyApplicationEvent> {

    @Override
    public void onApplicationEvent(MyApplicationEvent myApplicationEvent) {
        System.out.println("age: " + myApplicationEvent.getAge() + " name: " + myApplicationEvent.getName());
        System.out.println("收到消息：" + myApplicationEvent);
    }
}
