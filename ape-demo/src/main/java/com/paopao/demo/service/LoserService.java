package com.paopao.demo.service;

import cn.hutool.core.convert.Convert;
import com.paopao.demo.domain.LoserReq;
import com.paopao.demo.domain.LoserResp;
import com.paopao.excel.core.ExcelExportParams;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoserService {

    public Long getCount(ExcelExportParams<LoserReq, LoserResp> excelExportParams) {
        return 100L;
    }

    public List<LoserResp> selectListForExcelExport(ExcelExportParams<LoserReq, LoserResp> queryParams, Integer page) {
        if (page > 10) {
            return null;
        }
        LoserResp resp = new LoserResp();
        resp.setUserId(Convert.toLong(page));
        resp.setUserName("loser" + page);
        List<LoserResp> list = Lists.newArrayList();
        queryParams.getContext().put("user", page);

        for (int i = 0; i < 1000; i++) {
            list.add(resp);
        }
        return list;
    }
}
