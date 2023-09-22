package com.paopao.excel.constant;


/**
 * 导出常量
 *
 * @Author paopao
 * @Date 2023/07/25
 */
public interface IExcelConst {

    String PRE = "LOSER:E:";

    /**
     * 人工终止的任务（%s=工单ID）
     */
    String STOP_EXPORT_KEY = PRE + "STOP:EXCEL:%s";

    /**
     * 进行中工单统计（使用hash，小可以为工单ID）
     */
    String DOING_EXPORT_KEY = PRE + "DOING:EXCEL";

    /**
     * 人工终止的任务（%s=工单ID）
     */
    String TASK_INFO = PRE + "EXPORT:EXCEL:INFO:%s";

}
