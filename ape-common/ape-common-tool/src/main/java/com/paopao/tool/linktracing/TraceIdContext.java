package com.paopao.tool.linktracing;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @Author: paoPao
 * @Date: 2023/3/5
 * @Description: traceId实现逻辑
 */
public class TraceIdContext {
    public static final ThreadLocal<String> CURRENT_TRACE_ID = new InheritableThreadLocal<>();

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    public static String getTraceId() {
        return MDC.get(TraceIdConstant.TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(TraceIdConstant.TRACE_ID, traceId);
    }

    public static void clear() {
        CURRENT_TRACE_ID.set(null);
        CURRENT_TRACE_ID.remove();
    }
}
