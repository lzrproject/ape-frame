package com.paopao.excel.core;

import lombok.Data;

/**
 * 导出文件描述
 *
 * @Author loser
 * @Date 2023/07/25
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

    public ExcelFileDesc(String fileName, String category) {
        this.fileName = fileName;
        this.category = category;
    }

    private ExcelFileDesc(String fileName, String titleName, String sheetName, String category) {
        this.fileName = fileName;
        this.titleName = titleName;
        this.sheetName = sheetName;
        this.category = category;
    }

    public static ExcelFileDesc build(String fileName, String category) {
        return new ExcelFileDesc(fileName, category);
    }

    public static ExcelFileDesc build(String fileName, String titleName, String sheetName, String category) {
        return new ExcelFileDesc(fileName, titleName, sheetName, category);
    }

}
