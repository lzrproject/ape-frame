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
 * @Description: 过滤器记录请求耗时
 */
//@Component
//@Order(1)
@Slf4j
public class ApiRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("准备拦截web");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, responseWrapper);
        String contentType = responseWrapper.getContentType();
        byte[] content = responseWrapper.getResponseData();
        String str="";
        if (StringUtils.isNotBlank(contentType) && (contentType.contains(MediaType.APPLICATION_JSON_VALUE) || contentType.contains(MediaType.TEXT_HTML_VALUE))) {
            str = new String(content);
            str=str+"xiaoming";
            System.out.println("filter:"+str);
            HttpServletResponse response=(HttpServletResponse)servletResponse;
//            writeResponse(response,200,str);
        }
        long start = System.currentTimeMillis();
//        log.info("接口['{}']开始计时,req:{}", request.getRequestURI(), request.getParameterMap());
//        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();
        long requestTime = end - start;
//        log.info("{},response:{},costTime:{}", request.getRequestURI(), result, requestTime);
    }

    @Override
    public void destroy() {
        System.out.println("不行了，我要被销毁了");
    }
}
