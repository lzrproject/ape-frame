package com.paopao.excel.core;

import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import lombok.Data;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/10/13
 */
@Data
@SuppressWarnings("all")
public class ExcelExportParams<Params, ExportBean> {

    /**
     * 任务导出人
     */
    private String exportUser;

    /**
     * 需要导出数量
     */
    private Long exportCount;

    /**
     * 自定义参数
     */
    private Params params;

    /**
     * 文件描述信息
     */
    private ExcelFileDesc excelFileDesc;

    /**
     * 任务上下文
     */
    private ExportContext context;

    /**
     * 导出实体
     */
    private Class<ExportBean> exportBeanClass;

    /**
     * 分页查询数据类
     */
    private SearchExcelDataService exportServer;

    /**
     * 查询总数服务类
     */
    private CountExcelDataService countServer;

    public ExcelExportParams() {
    }

    private ExcelExportParams(String exportUser, Params params, Class<ExportBean> exportBeanClass,
                              SearchExcelDataService exportServer, CountExcelDataService countService, ExcelFileDesc fileDesc) {
        this.exportUser = exportUser;
//        this.exportCount = exportCount;
        this.params = params;
        this.exportBeanClass = exportBeanClass;
        this.exportServer = exportServer;
        this.countServer = countService;
        this.excelFileDesc = fileDesc;
    }

    private ExcelExportParams(String exportUser, Params params, Class<ExportBean> exportBeanClass,
                              SearchExcelDataService exportServer, ExcelFileDesc fileDesc, ExportContext context) {
        this.exportUser = exportUser;
//        this.exportCount = exportCount;
        this.params = params;
        this.exportBeanClass = exportBeanClass;
        this.exportServer = exportServer;
        this.excelFileDesc = fileDesc;
        this.context = context;
    }

    public static ExcelExportParams build(String user, Object params, Class<?> exportBeanClass,
                                          SearchExcelDataService exportServer, CountExcelDataService countService, ExcelFileDesc fileDesc) {
        return new ExcelExportParams(user, params, exportBeanClass, exportServer, countService, fileDesc);
    }

    public static ExcelExportParams build(String user, Object params, Class<?> exportBeanClass,
                                          SearchExcelDataService exportServer, ExcelFileDesc fileDesc, ExportContext context) {
        return new ExcelExportParams(user, params, exportBeanClass, exportServer, fileDesc, context);
    }
}
