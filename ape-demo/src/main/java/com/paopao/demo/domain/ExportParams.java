package com.paopao.demo.domain;

import lombok.Data;


/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/10/30
 */
@Data
public class ExportParams {

    private Long uuid;

    /**
     * 查询条件
     */
    private String conditionStr;

    /**
     * 根据年份查询(yyyy)
     */
    private String year;

    /**
     * 根据年份-月份查询(yyyy-MM)
     */
    private String yearMonth;

    /**
     * 需要导出总数量
     */
    private Long count;

    private Long start;
    private Integer size = 1000;

    public void getLimit(Long page, Integer size) {
//        String limit = "";
        if (page == null || size == null) {
            this.start = 0L;
            return;
        }
        if (page < 1) {
            page = 1L;
        }
        this.start = (page - 1) * (long) size;
        this.size = size;
//        limit += (page - 1) * size + "," + size;
//        return limit;
    }
}
