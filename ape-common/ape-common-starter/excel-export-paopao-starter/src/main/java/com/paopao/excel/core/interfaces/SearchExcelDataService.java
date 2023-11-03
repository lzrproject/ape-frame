package com.paopao.excel.core.interfaces;

import com.paopao.excel.core.ExcelExportParams;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导出服务类(需自定义)
 *
 * @Author paoPao
 * @Date 2023/10/9
 */
@Service
public interface SearchExcelDataService<Params, ExportBean> {

    /**
     * 导出查询数据接口(需自定义)
     *
     * @param queryParams 参数类型
     * @param page        页面数
     * @return 参数数据
     */
    List<ExportBean> selectListForExcelExport(ExcelExportParams<Params, ExportBean> queryParams, Integer page);
}
