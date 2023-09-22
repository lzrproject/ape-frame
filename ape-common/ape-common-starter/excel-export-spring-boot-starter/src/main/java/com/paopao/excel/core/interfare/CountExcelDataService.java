package com.paopao.excel.core.interfare;

import com.paopao.excel.core.ExcelExportParams;
import org.springframework.stereotype.Service;

/**
 * 查询本次导出的数据总量
 *
 * @Author paopao
 * @Date 2023/07/25
 */
@Service
public interface CountExcelDataService<Params, ExportBean> {

    /**
     * 数据总量
     */
    Long count(ExcelExportParams<Params, ExportBean> queryParams);

}
