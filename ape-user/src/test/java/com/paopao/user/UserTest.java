package com.paopao.user;

import com.paopao.user.controller.TestController;
import com.paopao.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description
 */
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TestController testController;

    @Test
    public void test1() {
        System.out.println(userService.count());
    }

    /**
     * 过期时间拦截
     */
    @Test
    public void requestRecord() {
        System.out.println(testController.requestTime(200L));
    }
}