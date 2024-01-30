package com.paopao.cda.utils;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
public class FileUtilCommon {

    public static List<String> readFile(String fileName) {
        File file = new File(fileName);
        return FileUtil.readLines(file, StandardCharsets.UTF_8);
    }

}
