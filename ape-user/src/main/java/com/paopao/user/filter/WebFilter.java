package com.paopao.user.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 111
 * @Date 2022/8/10 20:19
 * @Description 请求接口拦截测试（过滤器）
 */
//@Component
public class WebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
        System.out.println("web被我拦截了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 重写流方法
        JsonRequestWrapper wrapper = new JsonRequestWrapper((HttpServletRequest) servletRequest);

        /*
         * Step 2: 读取输入流，将所需信息写入
         * json形式参数填充
         */
        StringBuffer buffer = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        reader = wrapper.getReader();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        if (buffer.length() != 0) {
            JSONObject object = JSON.parseObject(buffer.toString());
            object.put("password", "222");
            wrapper.setBody(object.toString().getBytes());
        }


        /*
         * Step 3: 普通形式参数填充
         */

        HashMap<String, String[]> parameterMap = new HashMap<>(wrapper.getParameterMap());
        if (wrapper.getParameterMap().size() != 0) {
            parameterMap.put("value", new String[]{"222"});
        }
        ParameterRequestWrapper newRequest = new ParameterRequestWrapper(wrapper, parameterMap);


        System.out.println("web准备处理");
        filterChain.doFilter(newRequest,servletResponse);
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
        System.out.println("不行了，我要被销毁了");
    }
}
