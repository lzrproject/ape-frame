package com.paopao.excel.core.handler.upload;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 文件存储处理器
 *
 * @Author paoPao
 * @Date 2023/10/27
 */
public interface UploadFileHandler {

    String uploadWordBook(SXSSFWorkbook wb, String fileName, String path, String categoryType, String optUser);

//    void delTask(ExportTaskInfo taskInfo);
}
