package com.paopao.excel.core.interfaces;

import com.paopao.excel.core.ExcelExportParams;

/**
 * 查询本次导出的数据总量
 *
 * @Author paoPao
 * @Date 2023/10/30
 */
public interface CountExcelDataService<Params, ExportBean> {

    /**
     * 数据总量
     */
    Long count(ExcelExportParams<Params, ExportBean> queryParams);
}
