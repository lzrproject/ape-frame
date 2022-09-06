package com.paopao.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 111
 * @Date 2022/9/2 16:17
 * @Description
 */
@RestController
public class HelloWorld {

    @Value("${com.test}")
    private static String a;

    public static void main(String[] args) {
        System.out.println(a);
    }
}
