package com.paopao.demo.controller;

import com.paopao.demo.domain.LoserReq;
import com.paopao.demo.domain.LoserResp;
import com.paopao.demo.service.LoserService;
import com.paopao.excel.constant.CheckExportIsReturn;
import com.paopao.excel.core.ExcelExportServer;
import com.paopao.excel.core.ExcelFileDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/21
 */
@RestController
@RequestMapping("excel")
public class BigExcelController {

    @GetMapping("checkIsReturn")
    @CheckExportIsReturn
    public void checkIsReturn() {
        System.out.println("进入方法 checkIsReturn");
    }

    @Autowired
    private ExcelExportServer excelExportServer;

    @Autowired
    private LoserService loserService;

    @GetMapping("/export")
    public Long test() {
        LoserReq req = new LoserReq();
        req.setUuid(100L);
        String user = "loser";
//        LoserResp.class.get.getFields();
//        return null;
        return excelExportServer.exportBigExcel(
                user,
                req,
                LoserResp.class,
                loserService::getCount,
                loserService::selectListForExcelExport,
                ExcelFileDesc.build("用户数据表", "user")
        );
    }

}