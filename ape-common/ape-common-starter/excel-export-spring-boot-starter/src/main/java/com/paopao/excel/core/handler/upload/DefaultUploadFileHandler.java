package com.paopao.excel.core.handler.upload;

import cn.hutool.core.date.DateUtil;
import com.paopao.excel.aspect.ExcelExportAspect;
import com.paopao.excel.model.ExportTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 默认提供的文件存储处理器
 *
 * @Author loser
 * @Date 2023/07/25
 */
@Slf4j
public class DefaultUploadFileHandler implements UploadFileHandler {

    @Override
    public String uploadWordBook(SXSSFWorkbook wb, String fileName, String path, String categoryType, String optUser) {

        try {
            if (Objects.isNull(path)) {
                path = "/";
            }
            path = path + "/" + getTimeDir() + optUser + "/";
            if (StringUtils.isNotEmpty(categoryType)) {
                path = path + categoryType + "/";
            }
            File file = new File(path);
            // 如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                // 创建多级目录
                boolean mkdirs = file.mkdirs();
                log.info("uploadWordBook mkdirs {} res {}", path, mkdirs);
            }
            String filePath = path + fileName;
            FileOutputStream out = new FileOutputStream(filePath);
            wb.write(out);
            out.close();
        } catch (Exception e) {
            log.error("uploadWordBook catch throw an e", e);
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(wb)) {
                    wb.dispose();
                    wb.close();
                }
            } catch (IOException e) {
                log.error("uploadWordBook finally throw an e", e);
                e.printStackTrace();
            }
        }
        return path + fileName;
    }

    @Override
    public void delTask(ExportTaskInfo taskInfo) {

        File file = new File(taskInfo.getFilePath());
        if (file.exists()) {
            boolean delete = file.delete();
            log.info("del file {} res {}", taskInfo.getFilePath(), delete);
        }

    }

    private String getTimeDir() {
        return DateUtil.format(new Date(), "yyyy/MM/dd/");
    }

}
