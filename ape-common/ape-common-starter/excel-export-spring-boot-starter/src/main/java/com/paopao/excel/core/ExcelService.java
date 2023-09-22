package com.paopao.excel.core;


import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.paopao.excel.constant.CheckExportIsReturn;
import com.paopao.excel.core.interfare.SearchExcelDataService;
import com.paopao.excel.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 导出服务类
 *
 * @Author paopao
 * @Date 2023/07/25
 */
@Slf4j
public class ExcelService implements IExcelExportServer {

    /**
     * 大数据导出exportBigExcel，需要实现这个类
     *
     * @param queryParams
     * @param page
     * @return
     */
    @Override
    @SuppressWarnings("all")
    @CheckExportIsReturn
    public List<Object> selectListForExcelExport(Object queryParams, int page) {
        log.info("page:{}", page);
        ExcelExportParams params = (ExcelExportParams) queryParams;
        SearchExcelDataService exportServer = params.getExportServer();
        if (Objects.isNull(exportServer)) {
            ExceptionUtils.fail("导出业务类未设置");
        }
        return exportServer.selectListForExcelExport(params, page);
    }
}
