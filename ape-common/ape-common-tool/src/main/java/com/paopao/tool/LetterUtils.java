package com.paopao.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description:
 */
public class LetterUtils {

    /**
     * 字母转数字
     */
    public static long letterToNumber(String letter) {
        int length = letter.length();
        long number = 0;
        for (int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            int num = ch - 'A' + 1;
            number += num;
        }
        return number;
    }

    /**
     * 字符串拼接
     */
    public static String assembleHandler(char splitChar, String... args) {
        List<String> strList = Arrays.asList(args);
        return StringUtils.join(strList, splitChar);
    }

}
