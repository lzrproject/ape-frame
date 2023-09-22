package com.paopao.excel.core.interfare;

import com.paopao.excel.core.ExcelExportParams;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导出服务类
 *
 * @Author paopao
 * @Date 2023/07/25
 */
@Service
public interface SearchExcelDataService<Params, ExportBean> {

    /**
     * 导出查询数据接口
     *
     * @param queryParams 参数类型
     * @param page        页面数
     * @return 参数数据
     */
    List<ExportBean> selectListForExcelExport(ExcelExportParams<Params, ExportBean> queryParams, Integer page);

}
