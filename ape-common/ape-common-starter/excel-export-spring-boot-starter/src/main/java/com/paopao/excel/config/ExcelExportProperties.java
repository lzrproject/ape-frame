package com.paopao.excel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/9/20
 */
@Data
@ConfigurationProperties("paopao.export.excel")
public class ExcelExportProperties {

    private String targetPath;

    private long maxExportCount = 2000000;

    private String updateFileHandler = "com.paopao.excel.core.handler.upload.DefaultUploadFileHandler";
}
