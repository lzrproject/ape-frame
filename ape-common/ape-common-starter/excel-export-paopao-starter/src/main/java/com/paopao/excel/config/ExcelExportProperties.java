package com.paopao.excel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存配置 配置信息
 *
 * @Author paoPao
 * @Date 2023/10/27
 */
@Data
@ConfigurationProperties("paopao.export.excel")
public class ExcelExportProperties {

    /**
     * 文件上传的地址前缀
     */
    private String targetPath;

    /**
     * 导出最大数据量
     */
    private long maxExportCount = 2000000L;

    /**
     * 默认的文件上传地址
     */
    private String updateFileHandler = "com.paopao.excel.core.handler.upload.DefaultUploadFileHandler";
}
