package com.paopao.user.utils;

/**
 * @Author 111
 * @Date 2022/8/30 21:34
 * @Description
 */
public class ThreadLocalUtil {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
