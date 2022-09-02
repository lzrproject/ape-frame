package com.paopao.user.filter;

import com.paopao.user.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author 111
 * @Date 2022/8/10 20:55
 * @Description 请求接口拦截测试（拦截器）
 */

@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        //获取请求参数
//        String queryString = request.getQueryString();
//        log.info("请求参数:{}", queryString);
//
//        HashMap<String, String[]> map = new HashMap<>(request.getParameterMap());
//        map.put("value",new String[] { "222" });
//        new ParameterRequestWrapper(request);
//
//        //获取请求body
//        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
//        String body = new String(bodyBytes, request.getCharacterEncoding());
//
//        log.info("请求体：{}", body);

        ThreadLocalUtil.setCurrentId(111L);
        return true;
    }
}
