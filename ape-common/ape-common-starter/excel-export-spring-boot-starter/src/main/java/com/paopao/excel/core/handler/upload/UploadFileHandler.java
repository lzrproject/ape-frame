package com.paopao.excel.core.handler.upload;

import com.paopao.excel.model.ExportTaskInfo;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 文件存储处理器
 *
 * @Author loser
 * @Date 2023/07/25
 */
public interface UploadFileHandler {

    String uploadWordBook(SXSSFWorkbook wb, String fileName, String path, String categoryType, String optUser);

    void delTask(ExportTaskInfo taskInfo);

}
