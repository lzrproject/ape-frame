package com.paopao.demo.importa.core;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.paopao.demo.domain.MyDataModel;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.service.JhemrCda02Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @Author paoPao
 * @Date 2023/12/31
 */
@Service
public class ExcelImporterService {

    @Autowired
    private JhemrCda02Service jhemrCda02Service;

    public void doImport() {
        // Excel文件的路径
        String filePath = "E:\\idea\\demand\\2024\\01\\04\\paopao\\jhemr_cda02_2024_01_05_12_08_22_by_paopao.xlsx";

        // 需要读取的sheet数量
        int numberOfSheets = 6;

        // 创建一个固定大小的线程池，大小与sheet数量相同
        ExecutorService executor = Executors.newFixedThreadPool(numberOfSheets);

        // 遍历所有sheets
        for (int sheetNo = 0; sheetNo < numberOfSheets; sheetNo++) {
            // 在Java lambda表达式中使用的变量需要是final
            int finalSheetNo = sheetNo;
            // 向线程池提交一个任务
            executor.submit(() -> {
                // 使用EasyExcel读取指定的sheet
                EasyExcel.read(filePath, JhemrCda02VO.class, new MyDataModelListener(jhemrCda02Service))
                        .sheet(finalSheetNo) // 指定sheet号
                        .doRead(); // 开始读取操作
            });
        }
        // 启动线程池的关闭序列
        executor.shutdown();

        // 等待所有任务完成，或者在等待超时前被中断
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // 如果等待过程中线程被中断，打印异常信息
            e.printStackTrace();
        }
    }
}
