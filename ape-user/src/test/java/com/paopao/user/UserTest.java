package com.paopao.user;

import com.paopao.tool.PropertiesUtils;
import com.paopao.user.controller.TestController;
import com.paopao.user.entity.SysUser;
import com.paopao.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

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
        System.out.println(new HashMap<>().put("path", "aa"));
//        System.out.println(testController.requestTime(200L));
    }

    @Test
    public void propertiesTest() {
        PropertiesUtils instance = PropertiesUtils.getInstance();
        instance.setConfigPath("E:\\idea\\IdeaProjects\\java\\jqProject\\ape-frame\\ape-user\\src\\main\\resources");
        String value = instance.getPropertyValue("test", "ape.name");
        System.out.println(value);
    }

    @Test
    public void test() {
        System.out.println("B".charAt(0));
    }

}
