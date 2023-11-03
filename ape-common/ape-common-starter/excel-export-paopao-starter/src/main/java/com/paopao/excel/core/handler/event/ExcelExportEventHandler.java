package com.paopao.excel.core.handler.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 导出任务事件处理器(项目初始化后调用)
 *
 * @Author paoPao
 * @Date 2023/10/26
 */
public class ExcelExportEventHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ExcelExportEventHandler.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        contextRefreshedEvent.getApplicationContext().getBeansOfType(ExportExcelEventListener.class)
    }
}
