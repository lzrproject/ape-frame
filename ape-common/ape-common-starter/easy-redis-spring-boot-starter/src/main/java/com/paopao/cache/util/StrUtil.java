package com.paopao.cache.util;

import java.util.Objects;

/**
 * 字符串工具类
 *
 * @author loser
 * @date 2023/06/20
 */
public class StrUtil {

    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {

        return !isEmpty(str);
    }
}
