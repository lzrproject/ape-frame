package com.paopao.excel.core.csv;

import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;

/**
 * opencsv 导出服务
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@SuppressWarnings("all")
public class CsvExportServer {

    /**
     * 初始化导出内容
     */
    public Long exportBigExcel(String user, Object params, Class<?> exportBeanClass,
                               SearchExcelDataService exportServer, CountExcelDataService countService, ExcelFileDesc fileDesc) {
        return exportBigExcel(ExcelExportParams.build(user, params, exportBeanClass, exportServer, countService, fileDesc));
    }

    /**
     * 生成 excel 并保存文件
     */
    private Long exportBigExcel(ExcelExportParams<?, ?> params) {
        ExcelFileDesc fileDesc = params.getExcelFileDesc();
        String exportUser = params.getExportUser();
        return 1L;
    }
}
