package com.paopao.tool.timeout;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.paopao.tool.wrapper.HttpHelper;
import com.paopao.tool.wrapper.RequestWrapper;
import com.paopao.tool.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author paoPao
 * @Date 2023/2/22
 * @Description: 过滤器记录请求耗时  2、过滤器形式记录请求时间。
 */
@Slf4j
public class ApiRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("准备拦截web");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) servletResponse);
        long start = System.currentTimeMillis();
        String requestURI = requestWrapper.getRequestURI();
        log.info("接口['{}']开始计时,req:{}", requestURI, requestWrapper.getContent());
        filterChain.doFilter(requestWrapper, responseWrapper);
        String responseContent = responseWrapper.getContent();
        long end = System.currentTimeMillis();
        long requestTime = end - start;
        log.info("{},response:{},costTime:{}", requestURI, responseContent, requestTime);
        //将数据重新写入返回数据流中
        servletResponse.getOutputStream().write(responseWrapper.getResponseData());
    }

    @Override
    public void destroy() {
        System.out.println("不行了，我要被销毁了");
    }
}
