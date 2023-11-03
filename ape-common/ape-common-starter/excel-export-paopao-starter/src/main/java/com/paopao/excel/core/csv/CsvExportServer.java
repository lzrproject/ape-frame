package com.paopao.excel.core.csv;

import com.paopao.excel.config.ExcelExportProperties;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.ExcelService;
import com.paopao.excel.core.ExportServer;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;

/**
 * opencsv 导出服务
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@SuppressWarnings("all")
public class CsvExportServer extends ExportServer {

    public CsvExportServer(ExcelService excelService, UploadFileHandler uploadFileHandler,
                           ExcelExportProperties excelExportProperties) {
        super(excelService, uploadFileHandler, excelExportProperties);
//        this.server = excelService;
//        this.uploadFileHandler = uploadFileHandler;
//        this.excelExportProperties = excelExportProperties;
    }

    /**
     * 生成 excel 并保存文件
     */
    public Long exportBigExcel(ExcelExportParams<?, ?> params) {
        ExcelFileDesc fileDesc = params.getExcelFileDesc();
        String exportUser = params.getExportUser();
        // 校验参数
        super.checkParams(params);
        return 1L;
    }
}
