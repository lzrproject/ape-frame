package com.paopao.demo.controller;

import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.LoserResp;
import com.paopao.demo.domain.User;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.service.ExportService;
import com.paopao.demo.service.JhemrCda02Service;
import com.paopao.excel.core.ExcelExportServer;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @CheckExportIsReturn
    public void checkIsReturn() {
        System.out.println("进入方法 checkIsReturn");
    }

    @Autowired
    private ExportService exportService;

    @Autowired
    private JhemrCda02Service jhemrCda02Service;

    @GetMapping("/export")
    public Long test() {
        exportService.execBigExcel();
        return 1000L;
    }

    @PostMapping("test")
    public List<JhemrCda02VO> getUrl(@RequestBody ExportParams exportParams) {
        return jhemrCda02Service.getListByCondition(exportParams, 1L);
    }

}