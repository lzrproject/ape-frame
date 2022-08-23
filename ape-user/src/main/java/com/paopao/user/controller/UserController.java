package com.paopao.user.controller;

import com.paopao.user.entity.User;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.Interruptible;

/**
 * @Author 111
 * @Date 2022/8/10 16:40
 * @Description
 */

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("get")
    public String test(@RequestParam String value){
        System.out.println(value);
        return "Hello World!";
    }

    @PostMapping("entity")
    public void stringTest(@RequestBody User user){
        System.out.println(user.getPassword());
    }
}
