package com.paopao.demo.importa.core;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.service.JhemrCda02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/12/31
 */
@Service
@Slf4j
public class ExcelImporterService {

    @Autowired
    private JhemrCda02Service jhemrCda02Service;

    private Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public void doImport() {
        // Excel文件的路径
        String filePath = "E:\\idea\\demand\\2024\\01\\09\\paopao\\jhemr_cda02_2024_01_09_16_00_18_by_paopao.xlsx";

        // 需要读取的sheet数量
        int numberOfSheets = 6;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfSheets);
        StopWatch watch = new StopWatch();
        watch.start();
        AtomicLong aLong = new AtomicLong();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        // 遍历所有sheets
        for (int sheetNo = 0; sheetNo < numberOfSheets; sheetNo++) {
            int finalSheetNo = sheetNo;
            // 向线程池提交一个任务
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                // 使用EasyExcel读取指定的sheet
                EasyExcel.read(filePath, JhemrCda02VO.class, new MyDataModelListener(jhemrCda02Service, snowflake, aLong))
                        .headRowNumber(2)
                        .sheet(finalSheetNo)    // 指定sheet号
                        .doRead();  // 开始读取操作
            }, executor);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        watch.stop();
        log.info("ExcelImporterService.doImport size:{},time:{}", aLong.get(), watch.getTotalTimeMillis());
        System.out.println(aLong.get());
    }

    private void test() {

    }
}
