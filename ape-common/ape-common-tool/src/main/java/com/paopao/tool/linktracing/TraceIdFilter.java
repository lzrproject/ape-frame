package com.paopao.tool.linktracing;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: paoPao
 * @Date: 2023/3/5
 * @Description:
 */
@Slf4j
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader(TraceIdConstant.TRACE_ID);
        if (StringUtils.isBlank(header)) {
            header = TraceIdContext.generateTraceId();
        }
        TraceIdContext.setTraceId(header);
        filterChain.doFilter(servletRequest, servletResponse);
        TraceIdContext.clear();
    }
}
