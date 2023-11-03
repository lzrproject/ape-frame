package com.paopao.excel.config;

import com.paopao.excel.core.ExcelExportServer;
import com.paopao.excel.core.ExcelService;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * excel export 配置类
 *
 * @Author paoPao
 * @Date 2023/10/17
 */
@Configuration
@EnableConfigurationProperties({ExcelExportProperties.class})
public class ExcelExportConfiguration {

    private final ExcelExportProperties excelExportProperties;

    public ExcelExportConfiguration(ExcelExportProperties excelExportProperties) {
        this.excelExportProperties = excelExportProperties;
    }

    /**
     * 导出Excel实现类
     */
    @Bean
    public ExcelExportServer excelExportServer(ExcelService excelService, UploadFileHandler uploadFileHandler) {
        return new ExcelExportServer(excelService, uploadFileHandler, excelExportProperties);
    }

    /**
     * 服务类 service
     */
    @Bean
    public ExcelService excelService() {
        return new ExcelService();
    }

    /**
     * 服务类 service
     */
    @Bean
    public UploadFileHandler uploadFileHandler() throws Exception {
        return (UploadFileHandler) Class.forName(excelExportProperties.getUpdateFileHandler()).newInstance();
    }
}
