package com.paopao.excel.core.handler.event;

import com.alibaba.fastjson.JSONObject;
import com.paopao.excel.constant.ExportStatusEnum;
import com.paopao.excel.constant.IExcelConst;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.ExcelFileDesc;
import com.paopao.excel.core.ExportContext;
import com.paopao.excel.core.handler.listener.ExportExcelEventListener;
import com.paopao.excel.model.ExportTaskInfo;
import com.paopao.excel.utils.StringUtils;
import com.paopao.redis.core.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/20
 */
@Slf4j
public class ExcelExportEventHandler implements ApplicationListener<ContextRefreshedEvent> {

    private final RedisHandler redisHandler;

    private Collection<ExportExcelEventListener> exportExcelEventListeners;

    public ExcelExportEventHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        exportExcelEventListeners = contextRefreshedEvent.getApplicationContext().getBeansOfType(ExportExcelEventListener.class).values();
    }

    /**
     * 初始化一个工单并保存
     */
    public void initTaskInfoAndSave(Long taskId, String exportUser, ExcelExportParams<?, ?> queryParams, ExcelFileDesc excelFileDesc, String fileName, ExportContext ctx) {

        ExportTaskInfo taskInfo = new ExportTaskInfo();
        taskInfo.setId(taskId);
        taskInfo.setFileName(fileName);
        taskInfo.setUserId(exportUser);
        taskInfo.setStatus(ExportStatusEnum.INIT.getStatus());
        taskInfo.setCreateTime(System.currentTimeMillis());
        taskInfo.setSearchCondition(queryParams);
        taskInfo.setExportCategory(excelFileDesc.getCategory());
        redisHandler.set(String.format(IExcelConst.TASK_INFO, taskId), JSONObject.toJSONString(taskInfo), 86400000L);

        if (CollectionUtils.isEmpty(exportExcelEventListeners)) {
            return;
        }
        for (ExportExcelEventListener listener : exportExcelEventListeners) {
            try {
                listener.initEvent(taskInfo, ctx);
            } catch (Exception e) {
                log.error("handler initEvent throw a e data {}", JSONObject.toJSONString(taskInfo), e);
            }
        }
    }

    private ExportTaskInfo getExportTaskInfo(Long taskId) {
        String result = redisHandler.get(String.format(IExcelConst.TASK_INFO, taskId));
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JSONObject.parseObject(result, ExportTaskInfo.class);
    }

    /**
     * 更新任务状态
     */
    public void updateTaskStatus(Long taskId, String status, ExportContext ctx, Exception e) {

        ExportTaskInfo taskInfo = getExportTaskInfo(taskId);
        if (taskInfo == null) {
            return;
        }
        taskInfo.setId(taskId);
        taskInfo.setStatus(status);
        taskInfo.setUpdateTime(System.currentTimeMillis());
        redisHandler.set(String.format(IExcelConst.TASK_INFO, taskId), JSONObject.toJSONString(taskInfo), 86400000L);

        if (CollectionUtils.isEmpty(exportExcelEventListeners)) {
            return;
        }
        for (ExportExcelEventListener listener : exportExcelEventListeners) {
            try {
                if (Objects.equals(status, ExportStatusEnum.FAILURE.getStatus())) {
                    listener.failureEvent(taskInfo, ctx, e);
                } else if (Objects.equals(status, ExportStatusEnum.USER_STOP.getStatus())) {
                    listener.stopEvent(taskInfo, ctx);
                }
            } catch (Exception exception) {
                log.error("handler event throw a e data {}", JSONObject.toJSONString(taskInfo), exception);
            }
        }

    }

    /**
     * 更新任务的文件下载地址
     */
    public void updateTaskPath(Long taskId, String path, ExportContext ctx) {

        ExportTaskInfo taskInfo = getExportTaskInfo(taskId);
        if (taskInfo == null) {
            return;
        }
        taskInfo.setId(taskId);
        taskInfo.setFilePath(path);
        taskInfo.setUpdateTime(System.currentTimeMillis());
        taskInfo.setStatus(ExportStatusEnum.FINISH.getStatus());
        redisHandler.set(String.format(IExcelConst.TASK_INFO, taskId), JSONObject.toJSONString(taskInfo), 86400000L);

        if (CollectionUtils.isEmpty(exportExcelEventListeners)) {
            return;
        }
        for (ExportExcelEventListener listener : exportExcelEventListeners) {
            try {
                listener.finishEvent(taskInfo, ctx);
            } catch (Exception e) {
                log.error("handler finishEvent throw a e data {}", JSONObject.toJSONString(taskInfo), e);
            }
        }

    }

    /**
     * 更新进度
     */
    public void incrProgress(Long taskId, Long res, ExportContext ctx) {

        ExportTaskInfo taskInfo = getExportTaskInfo(taskId);
        if (taskInfo == null) {
            return;
        }
        taskInfo.setCurNum(res);

        if (CollectionUtils.isEmpty(exportExcelEventListeners)) {
            return;
        }
        for (ExportExcelEventListener listener : exportExcelEventListeners) {
            try {
                listener.doingEvent(taskInfo, ctx);
            } catch (Exception e) {
                log.error("handler finishEvent throw a e data {}", JSONObject.toJSONString(taskInfo), e);
            }
        }

    }

    public ExportTaskInfo delTask(Long taskId, ExportContext ctx) {

        ExportTaskInfo taskInfo = getExportTaskInfo(taskId);
        if (taskInfo == null) {
            return null;
        }
        redisHandler.del(String.format(IExcelConst.TASK_INFO, taskId));
        if (CollectionUtils.isEmpty(exportExcelEventListeners)) {
            return taskInfo;
        }
        for (ExportExcelEventListener listener : exportExcelEventListeners) {
            try {
                listener.delEvent(taskInfo, ctx);
            } catch (Exception exception) {
                log.error("handler event throw a e data {}", JSONObject.toJSONString(taskInfo), exception);
            }
        }
        return taskInfo;

    }
}
