package com.paopao.excel.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/21
 */
@Aspect
public class ExcelExportAspect {

    @Pointcut("@annotation(com.paopao.excel.constant.CheckExportIsReturn)")
    private void checkPoint() {

    }

    @Around(value = "checkPoint()")
    public Object checkExportIsReturn(ProceedingJoinPoint pjd) throws Throwable {
        Object proceed = pjd.proceed();
        // TODO 更新导出进度
        return proceed;
    }
}
