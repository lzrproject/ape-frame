package com.paopao.excel.config;

import com.paopao.excel.aspect.ExcelExportAspect;
import com.paopao.excel.core.ExcelExportServer;
import com.paopao.excel.core.ExcelService;
import com.paopao.excel.core.handler.event.ExcelExportEventHandler;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.redis.core.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * excel export 配置类
 *
 * @Author paoPao
 * @Date 2023/9/20
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = ExcelExportProperties.class)
public class ExcelExportConfig {

    private final ExcelExportProperties excelExportProperties;

    public ExcelExportConfig(ExcelExportProperties excelExportProperties) {
        this.excelExportProperties = excelExportProperties;
    }

    @Bean
    public ExcelExportAspect excelExportAspect(RedisHandler redisHandler, ExcelExportServer excelExportServer) {
        return new ExcelExportAspect(redisHandler, excelExportServer);
    }

    @Bean
    public ExcelService excelService() {
        return new ExcelService();
    }

    @Bean
    public UploadFileHandler uploadFileHandler() throws Exception {
        return (UploadFileHandler) Class.forName(excelExportProperties.getUpdateFileHandler()).newInstance();
    }

    @Bean
    public ExcelExportServer excelExportServer(RedisHandler redisHandler, ExcelExportEventHandler excelExportHandler,
                                               ExcelService excelService, UploadFileHandler uploadFileHandler) {
        return new ExcelExportServer(excelService, redisHandler, uploadFileHandler, excelExportHandler, excelExportProperties);
    }

    @Bean
    public ExcelExportEventHandler excelExportHandler(RedisHandler redisHandler) {
        return new ExcelExportEventHandler(redisHandler);
    }
}
