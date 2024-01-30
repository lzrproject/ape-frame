package com.paopao.cda;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.paopao.cda.domain.OperationData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/25
 */
public class ExcelTest {

    @Test
    public void readExcel() {
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\疾病分类编码国标(2020版本）.xls";
        List<OperationData> list = new ArrayList<>();
        EasyExcel.read(fileName, OperationData.class, new PageReadListener<OperationData>(dataList -> {
            list.addAll(dataList);
        })).sheet(1).doRead();
        System.out.println(list.size());
    }
}
