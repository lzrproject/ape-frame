package com.paopao.excel.core;

import lombok.Data;

/**
 * 导出文件描述
 *
 * @Author paoPao
 * @Date 2023/10/16
 */
@Data
public class ExcelFileDesc {

    /**
     * 导出接口对应的导出文件的文件名称
     */
    private String fileName;

    /**
     * 标题名称
     */
    private String titleName;

    /**
     * 表名称
     */
    private String sheetName;

    /**
     * 文件地址分类
     */
    private String category;

    private ExcelFileDesc() {}

    private ExcelFileDesc(String fileName, String titleName) {
        this.fileName = fileName;
        this.titleName = titleName;
    }

    private ExcelFileDesc(String fileName, String titleName, String sheetName, String category) {
        this.fileName = fileName;
        this.titleName = titleName;
        this.sheetName = sheetName;
        this.category = category;
    }

    public static ExcelFileDesc build(String fileName, String titleName) {
        return new ExcelFileDesc(fileName, titleName);
    }

    public static ExcelFileDesc build(String fileName, String titleName, String sheetName, String category) {
        return new ExcelFileDesc(fileName, titleName, sheetName, category);
    }
}
