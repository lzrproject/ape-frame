package com.paopao.user.controller;

import com.paopao.tool.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: paoPao
 * @Date: 2023/3/11
 * @Description:
 */
@RestController
@RequestMapping("properties")
@Slf4j
public class PropertiesController {

    @GetMapping("get")
    public String getApe() {
        PropertiesUtils instance = PropertiesUtils.getInstance();
        instance.setConfigPath("E:\\idea\\IdeaProjects\\java\\jqProject\\ape-frame\\ape-user\\src\\main\\resources");
        String value = instance.getPropertyValue("test", "ape.name");
        return value;
    }
}
