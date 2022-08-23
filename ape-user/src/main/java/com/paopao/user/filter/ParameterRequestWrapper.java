package com.paopao.user.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @Author 111
 * @Date 2022/8/14 16:49
 * @Description url地址参数封装工具
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());

//        renewParameterMap(request);
    }

    public ParameterRequestWrapper(HttpServletRequest request, Map<String, String[]> extendParams) {
        this(request);
        //这里将扩展参数写入参数表
        addAllParameters(extendParams);
    }

    @Override
    public String getParameter(String name) {
        String[] result = params.get(name);
        if (result == null || result.length == 0)
            return null;

        return result[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector<>(params.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;
    }

    //增加多个参数
    public void addAllParameters(Map<String, String[]> otherParams) {
        for (Map.Entry<String, String[]> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }


    //增加参数
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

}
