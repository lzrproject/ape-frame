package com.paopao.user.controller;

import com.paopao.response.Result;
import com.paopao.response.ResultCode;
import com.paopao.response.ResultMessage;
import com.paopao.user.entity.User;
import com.paopao.user.entity.dto.UserDto;
import com.paopao.user.entity.po.UserPo;
import com.paopao.user.entity.req.UserReq;
import com.paopao.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.Interruptible;

import java.util.List;

/**
 * @Author 111
 * @Date 2022/8/10 16:40
 * @Description
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("show")
    public Result<List<UserPo>> getUser() {
        return Result.success(true, ResultCode.SUCCESS, ResultMessage.SUCCESS, userService.list());
    }

    @PostMapping
    public Result addUser(@RequestBody UserReq userReq) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userReq,userDto);
        int i = userService.addUser(userDto);
        return Result.success(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean res = userService.removeById(id);
        return Result.success(res);
    }
}
