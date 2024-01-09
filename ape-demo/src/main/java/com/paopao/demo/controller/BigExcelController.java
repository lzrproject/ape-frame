package com.paopao.demo.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.importa.core.ExcelImporterService;
import com.paopao.demo.importa.core.MyDataModelListener;
import com.paopao.demo.service.ExportService;
import com.paopao.demo.service.JhemrCda02Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    private ExcelImporterService excelImporterService;

    @Autowired
    private JhemrCda02Service jhemrCda02Service;

    @GetMapping("/export")
    public Long test() {
        exportService.execBigExcel();
        return 1000L;
    }

    @GetMapping("/exportCsv")
    public Long csv() {
        exportService.execBigCsv();
        return 1000L;
    }
    private Snowflake snowflake = IdUtil.getSnowflake(1, 1);
    @GetMapping("importExcel")
    public void importExcel() {
        String filePath = "E:\\idea\\demand\\2024\\01\\05\\paopao\\jhemr_cda02_2024_01_05_12_08_22_by_paopao.xlsx";
        AtomicLong aLong = new AtomicLong();
        excelImporterService.doImport();
//        EasyExcel.read(filePath, JhemrCda02VO.class, new MyDataModelListener(jhemrCda02Service, snowflake, aLong))
//                .sheet(0) // 指定sheet号
//                .headRowNumber(2)
//                .doRead(); // 开始读取操作
    }

    @PostMapping("test")
    public List<JhemrCda02VO> getUrl(@RequestBody ExportParams exportParams) {
        return jhemrCda02Service.getListByCondition(exportParams, 1L);
    }

}