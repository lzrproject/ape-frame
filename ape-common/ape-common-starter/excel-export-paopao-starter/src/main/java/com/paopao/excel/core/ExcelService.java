package com.paopao.excel.core;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import com.paopao.excel.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 导出服务类
 *
 * @Author paoPao
 * @Date 2023/10/9
 */
@Slf4j
@SuppressWarnings("all")
public class ExcelService implements IExcelExportServer {

    @Override
    public List<Object> selectListForExcelExport(Object queryParams, int page) {
        ExcelExportParams params = (ExcelExportParams) queryParams;
        SearchExcelDataService exportServer = params.getExportServer();
        ExportContext context = params.getContext();
        if (Objects.isNull(exportServer)) {
            ExceptionUtils.fail("导出业务类未设置");
        }
        List list = exportServer.selectListForExcelExport(params, page);

        if (!CollectionUtils.isEmpty(list)) {
            log.info("导出第 {} 页，size:{}", page, context.getContext("curSize", Long.class));
        } else {
            log.info("========导出结束==========");
        }
        return list;
    }
}
