package com.paopao.excel.core;

import com.paopao.excel.core.interfare.CountExcelDataService;
import com.paopao.excel.core.interfare.SearchExcelDataService;
import lombok.Data;

/**
 * 导出参数
 *
 * @Author loser
 * @Date 2023/07/25
 */
@Data
@SuppressWarnings("all")
public class ExcelExportParams<Params, ExportBean> {

    /**
     * 任务工单id
     */
    private Long taskId;

    /**
     * 任务导出人
     */
    private String exportUser;

    /**
     * 导出数量
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
     * 查询总数服务类
     */
    private SearchExcelDataService exportServer;

    /**
     * 分页查询数据类
     */
    private CountExcelDataService countServer;

    private ExcelExportParams(
            Params params, Class<ExportBean> exportBeanClass,
            SearchExcelDataService exportServer, CountExcelDataService countServer,
            String exportUser, ExcelFileDesc excelFileDesc, ExportContext ctx) {
        this.params = params;
        this.exportBeanClass = exportBeanClass;
        this.exportServer = exportServer;
        this.countServer = countServer;
        this.exportUser = exportUser;
        this.excelFileDesc = excelFileDesc;
        this.context = ctx;
    }

    public ExcelExportParams() {
    }

    /**
     * 初始化
     * @param params
     * @param exportBeanClass
     * @param exportServer
     * @param countServer
     * @param user
     * @param excelFileDesc
     * @param ctx
     * @return
     */
    public static ExcelExportParams build(
            Object params, Class exportBeanClass,
            SearchExcelDataService exportServer, CountExcelDataService countServer,
            String user, ExcelFileDesc excelFileDesc, ExportContext ctx) {
        return new ExcelExportParams(params, exportBeanClass, exportServer, countServer, user, excelFileDesc, ctx);
    }
}
