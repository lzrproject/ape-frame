package com.paopao.excel.core;

import cn.hutool.core.date.DateUtil;
import com.paopao.excel.config.ExcelExportProperties;
import com.paopao.excel.core.csv.CsvExportServer;
import com.paopao.excel.core.excel.ExcelExportServer;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import com.paopao.excel.utils.ExceptionUtils;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@SuppressWarnings("all")
public abstract class ExportServer {

    /**
     * 公用线程池，用来异步执行一些非重要操作，这里只给150个线程，对于需要及时完成的任务应该使用专用线程池
     */
    public static final ExecutorService EXECUTOR_GENERAL = new ThreadPoolExecutor(10, 150,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), new ExcelThreadFactory("easy-export"));

    private final ExcelService server;
    private final UploadFileHandler uploadFileHandler;
    private final ExcelExportProperties excelExportProperties;

    public ExportServer(ExcelService excelService, UploadFileHandler uploadFileHandler,
                             ExcelExportProperties excelExportProperties) {
        this.server = excelService;
        this.uploadFileHandler = uploadFileHandler;
        this.excelExportProperties = excelExportProperties;
    }

    /**
     * 初始化导出内容
     */
    public Long exportBigExcel(String user, Object params, Class<?> exportBeanClass,
                               SearchExcelDataService exportServer, CountExcelDataService countService, ExcelFileDesc fileDesc) {
        return exportBigExcel(ExcelExportParams.build(user, params, exportBeanClass, exportServer, countService, fileDesc));
    }

    /**
     * 生成 excel 并保存文件
     */
    public abstract Long exportBigExcel(ExcelExportParams<?, ?> params);

    /**
     * 参数校验、注解校验
     *
     * @param params
     */
    protected void checkParams(ExcelExportParams<?, ?> params) {
        if (Objects.isNull(params)) {
            ExceptionUtils.fail("导出参数为空");
        }
        if (Objects.isNull(params.getExcelFileDesc())) {
            ExceptionUtils.fail("导出描述未定义");
        }
        if (Objects.isNull(params.getParams())) {
            ExceptionUtils.fail("导出自定义参数为空");
        }
        if (Objects.isNull(params.getExportBeanClass())) {
            ExceptionUtils.fail("导出实体未定义");
        }
        if (Objects.isNull(params.getExportServer())) {
            ExceptionUtils.fail("导出服务类不存在");
        }
//        if (Objects.isNull(params.getCountServer())) {
//            ExceptionUtils.fail("查询总数类不存在");
//        }

//        Field[] fields = params.getExportBeanClass().getDeclaredFields();
//        boolean flag = true;
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(Excel.class)) {
//                flag = false;
//                break;
//            }
//        }
    }

}