package com.paopao.demo.service;

import com.google.common.base.Preconditions;
import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.excel.ExcelExportServer;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.ExportContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExportService {

    @Autowired
    private UserService userService;

    @Autowired
    private JhemrCda02Service jhemrCda02Service;

    @Autowired
    private ExcelExportServer excelExportServer;

    public Long getCount(ExcelExportParams<ExportParams, JhemrCda02VO> queryParams) {
//        ExportParams params = queryParams.getParams();
//        String conditionStr = params.getConditionStr();
        return 10L;
    }

    public List<JhemrCda02VO> selectListForExcelExport(ExcelExportParams<ExportParams, JhemrCda02VO> queryParams, long page) {
//        if (page > 10) {
//            return null;
//        }
//        LoserResp resp = new LoserResp();
//        resp.setUserId(Convert.toLong(page));
//        resp.setUserName("loser" + page);
//        List<LoserResp> list = Lists.newArrayList();
//        queryParams.getContext().put("user", page);
//
//        for (int i = 0; i < 1000; i++) {
//            list.add(resp);
//        }
        //        queryParams.getContext().put("currentCount", pageList.getSize());
        // 自定义参数
        ExportParams params = queryParams.getParams();
        // 导出数量
        Long exportCount = params.getCount();
        // 导出上下文
        ExportContext context = queryParams.getContext();
        // 当前已导出数量
        Long curSize = context.getContext("curSize", Long.class);
        if (curSize != null && curSize > exportCount) {
            return null;
        }
        List<JhemrCda02VO> cda02VOS = jhemrCda02Service.getListByCondition(params, page);
        context.put("curSize", curSize == null ? cda02VOS.size() : curSize + cda02VOS.size());
        return cda02VOS;
    }

    public Long execBigExcel() {
        ExportParams req = new ExportParams();
        req.setUuid(100L);
        req.setYear("2023");
        req.setCount(500000L);
        this.checkParams(req);
        String userName = "paopao";
//        LoserResp.class.get.getFields();
        return excelExportServer.exportBigExcel(
                userName,
                req,
                JhemrCda02VO.class,
                this::selectListForExcelExport,
                ExcelFileDesc.build("jhemr_cda02", "user")
        );
    }

    private void checkParams(ExportParams exportParams) {
        String year = exportParams.getYear();
        String yearMonth = exportParams.getYearMonth();
        Long count = exportParams.getCount();
        if (year == null && yearMonth == null) {
            throw new IllegalArgumentException("年份和月份字段参数不能为NULL");
        }
        if (year != null && yearMonth != null) {
            exportParams.setYearMonth(null);
        }
        Preconditions.checkNotNull(count, "查询数量不允许为NULL");
    }
}
