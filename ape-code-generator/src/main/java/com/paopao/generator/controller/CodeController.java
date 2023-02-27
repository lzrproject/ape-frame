package com.paopao.generator.controller;

import com.paopao.generator.entity.Param;
import com.paopao.generator.util.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("codeGenerator")
@Slf4j
public class CodeController {

    @Autowired
    private GeneratorUtil generatorUtil;

    //
//    @Value("${entity.home}")
//    private String home;
//
    @PostMapping("create")
    public void create(@RequestBody Param param) {
        String modelName = param.getModelName();
        String[] tableName = param.getTableName();
        String tablePrefix = param.getTablePrefix();
        String url = param.getUrl();
        String driverClass = param.getDriverClass();
        String username = param.getUsername();
        String password = param.getPassword();
        generatorUtil.create(modelName, tablePrefix, url, driverClass, username, password, tableName);
    }
//
//    public static void main(String[] args) {
//        String osName = System.getProperties().getProperty("os.name");
//        System.out.println(osName.toLowerCase().contains("window"));
//
////        Runtime runtime = Runtime.getRuntime();
////        try {
////            runtime.exec("cmd /k cd D:/Program Files/IdeaProjects/java/" + "applyKey-entity" + " && mvn clean && mvn package");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
}
