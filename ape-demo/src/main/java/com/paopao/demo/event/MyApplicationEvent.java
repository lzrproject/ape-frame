package com.paopao.demo.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 事件
 *
 * @Author paoPao
 * @Date 2023/11/27
 */
@Getter
@Setter
public class MyApplicationEvent extends ApplicationEvent {

    private Integer age;
    private String name;

    public MyApplicationEvent(Object source, String name, Integer age) {
        super(source);
        this.age = age;
        this.name = name;
    }
}
