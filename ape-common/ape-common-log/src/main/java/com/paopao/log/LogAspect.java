package com.paopao.log;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Author: paoPao
 * @Date: 2022/11/13
 * @Description: 日志切面
 */

@Aspect
@Slf4j
@Component
@ConditionalOnProperty(name = {"log.aspect.enable"}, havingValue = "true", matchIfMissing = false)
public class LogAspect {

    @Pointcut("execution(* com.paopao.*.controller.*Controller.*(..)) || execution(* com.paopao.*.service.*Service.*(..))")
    private void pointCut() {

    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取参数
        Object[] args = pjp.getArgs();
        String req = new Gson().toJson(args);
        // 获取方法信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String methodName = methodSignature.getDeclaringType().getName() + "." + methodSignature.getName();
        log.info("{},req:{}", methodName, req);
        Long start = System.currentTimeMillis();
        // 响应内容
        Object proceed = pjp.proceed();
        String resp = new Gson().toJson(proceed);
        long end = System.currentTimeMillis();
        log.info("{},response:{},costTime:{}", methodName, resp, end - start);

    }
}
