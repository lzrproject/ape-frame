package com.paopao.excel.core.csv;

import cn.hutool.core.date.DateUtil;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.paopao.excel.config.ExcelExportProperties;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.ExcelService;
import com.paopao.excel.core.ExportServer;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * opencsv 导出服务
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@SuppressWarnings("all")
@Slf4j
public class CsvExportServer extends ExportServer {

    private static final String extName = ".csv";

//    private final UploadFileHandler uploadFileHandler;

    public CsvExportServer(UploadFileHandler uploadFileHandler,
                           ExcelExportProperties excelExportProperties) {
        super(excelExportProperties);
//        this.server = excelService;
//        this.uploadFileHandler = uploadFileHandler;
//        this.excelExportProperties = excelExportProperties;
    }

    /**
     * 生成 excel 并保存文件
     */
    public Long exportBigExcel(ExcelExportParams<?, ?> params) {
        ExcelFileDesc excelFileDesc = params.getExcelFileDesc();
        String exportUser = params.getExportUser();
        // 校验参数
        super.checkParams(params);
        // 获取文件名
        String fileName = genFileName(excelFileDesc, exportUser);
        // 导出并下载
        this.exportAndUpload(params, excelFileDesc, null, fileName, exportUser);
        return 1L;
    }

    private String genFileName(ExcelFileDesc excelFileDesc, String exportUser) {
        return excelFileDesc.getFileName() + "_" + DateUtil.format(new Date(), "yyyy_MM_dd_HH_mm_ss") + "_by_" + exportUser + extName;
    }

    @Override
    public void exportAndUpload(ExcelExportParams<?, ?> params, ExcelFileDesc excelFileDesc, String encryptStr, String fileName, String optUser) {
        StopWatch watch = new StopWatch();
        watch.start();
        EXECUTOR_GENERAL.execute(() -> {
            log.info("========开始导出文件==========");
            // 01 生成文件并导出文件
            CsvExportBuild.exportBigCsv(excelExportProperties.getTargetPath(), params, fileName);
            watch.stop();
            log.info("export success fileName:{},time:{}", fileName, watch.getTotalTimeMillis() + "ms");
        });
    }


}
