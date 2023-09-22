package com.paopao.excel.core.handler.listener;


import com.paopao.excel.core.ExportContext;
import com.paopao.excel.model.ExportTaskInfo;

/**
 * 导出事件监听
 *
 * @author loser
 * @Date 2023/07/25
 */
public interface ExportExcelEventListener {

    /**
     * 任务初始化事件
     *
     * @param info 任务信息
     */
    default void initEvent(ExportTaskInfo info, ExportContext ctx) {
    }

    /**
     * 任务进行中事件
     *
     * @param info 任务信息
     */
    default void doingEvent(ExportTaskInfo info, ExportContext ctx) {
    }

    /**
     * 任务完成事件
     *
     * @param info 任务信息
     */
    default void finishEvent(ExportTaskInfo info, ExportContext ctx) {
    }

    /**
     * 任务异常失败事件
     *
     * @param info 任务信息
     * @param e    异常
     */
    default void failureEvent(ExportTaskInfo info, ExportContext ctx, Exception e) {
    }

    /**
     * 任务删除事件
     *
     * @param info 任务信息
     */
    default void delEvent(ExportTaskInfo info, ExportContext ctx) {
    }

    /**
     * 任务终止事件
     *
     * @param info 任务信息
     */
    default void stopEvent(ExportTaskInfo info, ExportContext ctx) {
    }

}
