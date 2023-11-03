package com.paopao.excel.utils;


import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * 字符串工具类
 *
 * @author loser
 * @date 2023/07/25
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 安全的进行字符串 format
     *
     * @param target 目标字符串
     * @param params format 参数
     * @return format 后的
     */
    public static String format(String target, Object... params) {
        if (target.contains("%s") && ArrayUtils.isNotEmpty(params)) {
            return String.format(target, params);
        }
        return target;
    }

}
