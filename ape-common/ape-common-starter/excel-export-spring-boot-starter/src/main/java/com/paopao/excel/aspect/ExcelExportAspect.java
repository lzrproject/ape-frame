package com.paopao.excel.aspect;

import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.paopao.excel.constant.IExcelConst;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.ExcelExportServer;
import com.paopao.excel.utils.ExceptionUtils;
import com.paopao.redis.core.RedisHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Objects;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/21
 */
@Aspect
public class ExcelExportAspect {

    private final RedisHandler redisHandler;
    private final ExcelExportServer excelExportServer;

    public ExcelExportAspect(RedisHandler redisHandler, ExcelExportServer excelExportServer) {
        this.redisHandler = redisHandler;
        this.excelExportServer = excelExportServer;
    }

    @Pointcut("@annotation(com.paopao.excel.constant.CheckExportIsReturn)")
    private void checkPoint() {

    }

    @Around(value = "checkPoint()")
    public Object checkExportIsReturn(ProceedingJoinPoint pjd) throws Throwable {

        // 01 获取任务ID
        Long taskId = getTaskId(pjd.getArgs());

        // 02 导出手动中断业务判断
        check(taskId);

        // 03 获取写入数据量
        Object result = pjd.proceed();

        // 04 更新任务的进度
        setTaskProgress(taskId, getFinishCount(result));
        return result;

    }

    private void check(Long taskId) {
        if (Objects.isNull(taskId)) {
            ExceptionUtils.fail("工单id为空");
        }
        String key = String.format(IExcelConst.STOP_EXPORT_KEY, taskId);
        try {
            if (redisHandler.exists(key)) {
                throw new ExcelExportException("任务已终止");
            }
        } finally {
            redisHandler.del(key);
        }
    }

    private static int getFinishCount(Object result) {
        int finishCount = 0;
        if (result instanceof List) {
            int size = 0;
            if (ObjectUtil.isNotNull(result)) {
                List<?> list = (List<?>) result;
                size = list.size();
            }
            finishCount = size;
        }
        return finishCount;
    }

    private static Long getTaskId(Object[] args) {
        Long taskId = 0L;
        for (Object obj : args) {
            if (obj instanceof ExcelExportParams) {
                ExcelExportParams<?, ?> exportPrams = (ExcelExportParams<?, ?>) obj;
                taskId = exportPrams.getTaskId();
            }
        }
        return taskId;
    }

    /**
     * 更新任务的进度
     */
    private void setTaskProgress(Long taskId, int finishCount) {

        if (finishCount > 0) {
            Long res = redisHandler.hincrBy(IExcelConst.DOING_EXPORT_KEY, Convert.toStr(taskId), finishCount);
            redisHandler.expire(IExcelConst.DOING_EXPORT_KEY, 60);
            excelExportServer.incrProgress(taskId, res);
        }

    }
}
