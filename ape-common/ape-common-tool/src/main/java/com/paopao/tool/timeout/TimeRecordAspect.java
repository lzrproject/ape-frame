package com.paopao.tool.timeout;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author: paoPao
 * @Date: 2023/2/21
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class TimeRecordAspect {

    //用ThreadLocal记录当前线程访问接口的开始时间
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before("@annotation(controllerTimeRecord)")
    public void doBefore(JoinPoint point, TimeRecord controllerTimeRecord) throws Throwable {
        //记录接口的开始时间
        startTime.set(System.currentTimeMillis());
    }

    //接口方法执行完成之后
    @After("@annotation(controllerTimeRecord)")
    public void doAfter(JoinPoint joinPoint, TimeRecord controllerTimeRecord){
        //将当前的事件减去之前的事件
        log.info("{}访问时间为：{}ms",joinPoint.getSignature().getName(),(System.currentTimeMillis()-startTime.get()));
    }

}
