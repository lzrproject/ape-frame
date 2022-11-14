package com.paopao.tool.date;

import java.text.SimpleDateFormat;

/**
 * @Author paoPao
 * @Date 2022/11/14
 * @Description
 */
public class SimpleDateFormatUtils {

    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static SimpleDateFormat getTime() {
        SimpleDateFormat simpleDateFormat = THREAD_LOCAL.get();
        if(simpleDateFormat == null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat;
    }

}
