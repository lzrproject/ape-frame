package com.paopao.excel;

import com.paopao.excel.constant.CheckExportIsReturn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/21
 */
@RestController
@RequestMapping("aspect")
public class ExcelAspect {

    @RequestMapping("test")
    @CheckExportIsReturn
    public void aspectTest() {
        System.out.println("111");
    }
}
