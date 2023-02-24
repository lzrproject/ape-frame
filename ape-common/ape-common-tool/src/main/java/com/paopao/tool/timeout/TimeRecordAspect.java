package com.paopao.tool.timeout;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: paoPao
 * @Date: 2023/2/21
 * @Description: 多个切面，容易造成返回值覆盖的情况
 */
@Aspect
//@Component
@Slf4j
public class TimeRecordAspect {

    //用ThreadLocal记录当前线程访问接口的开始时间
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Around("@annotation(controllerTimeRecord)")
    public Object doAround(ProceedingJoinPoint joinPoint, TimeRecord controllerTimeRecord) throws Throwable {
        // 获取参数
        Object[] args = joinPoint.getArgs();
        String req = new Gson().toJson(args);
        // 获取方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getDeclaringType().getName() + "." + methodSignature.getName();
        log.info("接口['{}']开始计时,req:{}", methodName, req);
        long start = System.currentTimeMillis();
        // 响应内容
        Object proceed = joinPoint.proceed();
        String resp = new Gson().toJson(proceed);
        long end = System.currentTimeMillis();
        long requestTime = end - start;
        log.info("{},response:{},costTime:{}", methodName, resp, requestTime);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("body", proceed);
        return map;
    }

//    @Before("@annotation(controllerTimeRecord)")
//    public void doBefore(JoinPoint point, TimeRecord controllerTimeRecord) throws Throwable {
//        log.info("接口['{}']开始计时", point.getSignature().getName());
//        //记录接口的开始时间
//        startTime.set(System.currentTimeMillis());
//    }
//
//    //接口方法执行完成之后
//    @After("@annotation(controllerTimeRecord)")
//    public void doAfter(JoinPoint joinPoint, TimeRecord controllerTimeRecord) {
//        //将当前的事件减去之前的事件
//        log.info("{}访问时间为：{}ms", joinPoint.getSignature().getName(), (System.currentTimeMillis() - startTime.get()));
//    }

}
