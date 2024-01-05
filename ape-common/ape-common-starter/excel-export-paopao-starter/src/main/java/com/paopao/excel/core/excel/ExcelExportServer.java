package com.paopao.excel.core.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.paopao.excel.config.ExcelExportProperties;
import com.paopao.excel.core.*;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.excel.core.interfaces.CountExcelDataService;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import com.paopao.excel.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * cms easyPoi excel 导出服务
 *
 * @Author paoPao
 * @Date 2023/10/13
 */
@Slf4j
@SuppressWarnings("all")
public class ExcelExportServer extends ExportServer {

    private static final String extName = ".xlsx";

    /**
     * 公用线程池，用来异步执行一些非重要操作，这里只给150个线程，对于需要及时完成的任务应该使用专用线程池
     */
//    public static final ExecutorService EXECUTOR_GENERAL = new ThreadPoolExecutor(10, 150,
//            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), new ExcelThreadFactory("easy-export"));
//
    private final ExcelService server;
    private final UploadFileHandler uploadFileHandler;
//    private final ExcelExportProperties excelExportProperties;
//
    public ExcelExportServer(ExcelService excelService, UploadFileHandler uploadFileHandler,
                             ExcelExportProperties excelExportProperties) {
        super(excelExportProperties);
        this.server = excelService;
        this.uploadFileHandler = uploadFileHandler;
//        this.excelExportProperties = excelExportProperties;
    }

//    /**
//     * 初始化导出内容
//     */
//    public Long exportBigExcel(String user, Object params, Class<?> exportBeanClass,
//                               SearchExcelDataService exportServer, CountExcelDataService countService, ExcelFileDesc fileDesc) {
//        return exportBigExcel(ExcelExportParams.build(user, params, exportBeanClass, exportServer, countService, fileDesc));
//    }
//
//    /**
//     * 初始化导出内容(无统计数量)
//     */
//    public Long exportBigExcel(String user, Object params, Class<?> exportBeanClass,
//                               SearchExcelDataService exportServer, ExcelFileDesc fileDesc) {
//        ExportContext context = ExportContext.empty();
//        return exportBigExcel(ExcelExportParams.build(user, params, exportBeanClass, exportServer, fileDesc, context));
//    }

    /**
     * 生成 excel 并保存文件
     */
    @Override
    public Long exportBigExcel(ExcelExportParams<?, ?> params) {
        ExcelFileDesc excelFileDesc = params.getExcelFileDesc();
        String exportUser = params.getExportUser();
        // 校验参数
        this.checkParams(params);
        // 获取导出数据量
        CountExcelDataService countServer = params.getCountServer();
        if (ObjectUtil.isNotNull(countServer)) {
            Long dataTotal = countServer.count(params);
            params.setExportCount(dataTotal);
        }
        // 获取文件名
        String fileName = genFileName(excelFileDesc, exportUser);
        // 导出并下载
        this.exportAndUpload(params, excelFileDesc, null, fileName, exportUser);
        return 1L;
    }

    private String genFileName(ExcelFileDesc excelFileDesc, String exportUser) {
        return excelFileDesc.getFileName() + "_" + DateUtil.format(new Date(), "yyyy_MM_dd_HH_mm_ss") + "_by_" + exportUser + extName;
    }

    /**
     * 异步生成文件并上传
     */
    @Override
    public void exportAndUpload(ExcelExportParams<?, ?> params, ExcelFileDesc excelFileDesc, String encryptStr, String fileName, String optUser) {
        StopWatch watch = new StopWatch();
        watch.start();
//        long start = System.currentTimeMillis();
//        Long taskId = params.getTaskId();
        // 开始导出
        EXECUTOR_GENERAL.execute(() -> {
            try {
                log.info("========开始导出文件==========");
                Long exportCount = params.getExportCount();
                // 01 生成文件
                SXSSFWorkbook workbook = buildWorkbook(excelFileDesc.getTitleName(), excelFileDesc.getSheetName(), params.getExportBeanClass(),
                        server, params);
                // 02 上传文件并更新任务下载地址
                uploadAndUpdateTask(workbook, fileName, 0L, excelFileDesc.getCategory(), optUser);
                watch.stop();
                log.info("export success fileName:{},time:{}", fileName, watch.getTotalTimeMillis() + "ms");
            } catch (Exception e) {
                watch.stop();
                log.error("exportBigExcel stopTaskByUser taskId:{} timeOut:{}", 0, watch.getLastTaskTimeMillis(), e);
            }
        });
    }

    /**
     * 构建一个excel workBook（使用后得关闭流）
     *
     * @param titleName   标题名
     * @param sheetName   表名
     * @param pojoClass   导出实体类（需要添加easyPoi的 @Excel）
     * @param server      查询数据的实现类（需要实现selectListForExcelExport方法）
     * @param queryParams 查询数据的参数
     */
    private SXSSFWorkbook buildWorkbook(String titleName, String sheetName, Class<?> pojoClass, IExcelExportServer server, Object queryParams) {
        ExportParams params = new ExportParams(titleName, sheetName);
        params.setMaxNum(500000);
        return (SXSSFWorkbook) ExcelExportUtil.exportBigExcel(params, pojoClass, server, queryParams);
    }

    /**
     * 上传文件后更新记录
     */
    private void uploadAndUpdateTask(SXSSFWorkbook workbook, String fileName, Long taskId, String category, String optUser) {

        String path = uploadFileHandler.uploadWordBook(workbook, fileName, super.excelExportProperties.getTargetPath(), category, optUser);
//        excelExportHandler.updateTaskPath(taskId, path, getContext(taskId));

    }
}
