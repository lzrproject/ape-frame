package com.paopao.excel.core;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.paopao.excel.config.ExcelExportProperties;
import com.paopao.excel.constant.ExcelExportException;
import com.paopao.excel.constant.ExportStatusEnum;
import com.paopao.excel.core.handler.event.ExcelExportEventHandler;
import com.paopao.excel.core.handler.upload.UploadFileHandler;
import com.paopao.excel.core.interfare.CountExcelDataService;
import com.paopao.excel.core.interfare.SearchExcelDataService;
import com.paopao.excel.utils.ExceptionUtils;
import com.paopao.redis.core.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * cms easyPoi excel 导出服务
 *
 * @Author loser
 * @Date 2023/07/25
 */
@Slf4j
public class ExcelExportServer {

    /**
     * 用户生成雪花ID
     */
    private static final long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) % 32;

    private static final String extName = ".xlsx";

    private final ExcelService server;
    private final RedisHandler redisHandler;
    private final UploadFileHandler uploadFileHandler;
    private final ExcelExportEventHandler excelExportHandler;
    private final ExcelExportProperties excelExportProperties;

    public static final Cache<Long, ExportContext> CONTEXT_CACHE = Caffeine.newBuilder()
            .initialCapacity(16).maximumSize(256) // 缓存的最大容量
            .expireAfterWrite(Duration.ofHours(1)).build(); // 在最后一次写入缓存后开始计时，在指定的时间后过期

    public static final ExecutorService EXECUTOR_GENERAL = new ThreadPoolExecutor(10, 150,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1000), new CustomNameThreadFactory("easy-export"));

    public ExcelExportServer(ExcelService server, RedisHandler redisHandler, UploadFileHandler uploadFileHandler, ExcelExportEventHandler excelExportHandler, ExcelExportProperties excelExportProperties) {
        this.server = server;
        this.redisHandler = redisHandler;
        this.uploadFileHandler = uploadFileHandler;
        this.excelExportHandler = excelExportHandler;
        this.excelExportProperties = excelExportProperties;
    }

    /**
     * 重载
     */
    @SuppressWarnings("all")
    public Long exportBigExcel(
            String user, Object params, Class<?> exportBeanClass, CountExcelDataService countServer,
            SearchExcelDataService exportServer, ExcelFileDesc type) {
        ExportContext ctx = ExportContext.empty();
        return exportBigExcel(ExcelExportParams.build(params, exportBeanClass, exportServer, countServer, user, type, ctx));
    }

    /**
     * 重载
     */
    @SuppressWarnings("all")
    public Long exportBigExcel(
            String user, Object params, Class<?> exportBeanClass, CountExcelDataService countServer,
            SearchExcelDataService exportServer, ExcelFileDesc type, ExportContext ctx) {
        return exportBigExcel(ExcelExportParams.build(params, exportBeanClass, exportServer, countServer, user, type, ctx));
    }

    private Long exportBigExcel(ExcelExportParams<?, ?> params) {
        // 01 参数校验
        checkParams(params);
        ExcelFileDesc excelFileDesc = params.getExcelFileDesc();
        String encryptStr = null;
        Long taskId = null;
        String exportUser = params.getExportUser();
        boolean lock = false;
        try {
            // 02 检测导出数量
            Long exportCount = getTotalCount(params);
            checkExportCount(exportCount, excelExportProperties.getMaxExportCount());

            // 03 初始化部分工单信息
            taskId = IdUtil.createSnowflake(workerId, 1).nextId();
            CONTEXT_CACHE.put(taskId, params.getContext());
            params.setTaskId(taskId);
            params.setExportUser(exportUser);
            params.setExportCount(exportCount);
            encryptStr = limitExportByParams(excelFileDesc.getFileName(), exportUser);
            // TODO 可以考虑换成 Redission
            lock = redisHandler.lock(encryptStr, encryptStr, 600L);
            if (!lock) {
                ExceptionUtils.fail("表格正在导出中，请稍等一会!");
            }
            String fileName = genFileName(excelFileDesc, exportUser);
            // 04 构建任务
            buildTaskInfo(taskId, exportUser, params, excelFileDesc, fileName);
            lock = false;
            // 05 上传文件
            exportAndUpload(params, excelFileDesc, encryptStr, fileName, exportUser);

        } catch (ExcelExportException e) {
            ExceptionUtils.fail(e);
        } catch (Exception e) {
            // 04 终止任务并更新任务为未知异常
            stopTaskByException(taskId, e);
            ExceptionUtils.fail(e);
        } finally {
            if (lock) {
                redisHandler.releaseLock(encryptStr, encryptStr);
            }
        }
        return null;
    }

    private static Long getTotalCount(ExcelExportParams<?, ?> params) {
        return params.getCountServer().count(params);
    }

    private String genFileName(ExcelFileDesc excelFileDesc, String exportUser) {
        return excelFileDesc.getFileName() + "_" + DateUtil.format(new Date(), "yyyy_MM_dd_HH_mm_ss") + "_by_" + exportUser + extName;
    }

    private void checkParams(ExcelExportParams<?, ?> params) {
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
        if (Objects.isNull(params.getCountServer())) {
            ExceptionUtils.fail("查询总数类不存在");
        }

        Field[] fields = params.getExportBeanClass().getFields();
        boolean flag = true;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Excel.class)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ExceptionUtils.fail("导出实体缺少 Excel 注解");
        }
    }

    /**
     * 对导出请求的参数进行base64加密确定唯一请求
     *
     * @param type   导出接口的类型
     * @param params 限制参数
     * @see ExcelFileDesc type 对应的值
     */
    private String limitExportByParams(String type, Object... params) {

        if (ArrayUtil.isEmpty(params)) {
            ExceptionUtils.fail("参数错误");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (Object param : params) {
            sb.append(JSON.toJSONString(param));
        }
        return "EXPORT:" + Base64.encode(sb.toString());
    }

    /**
     * 异步生成文件并上传
     */
    private void exportAndUpload(ExcelExportParams<?, ?> params, ExcelFileDesc excelFileDesc, String encryptStr, String fileName, String optUser) {
        long start = System.currentTimeMillis();
        Long taskId = params.getTaskId();
        EXECUTOR_GENERAL.execute(() -> {
            try {
                // 01 生成文件
                SXSSFWorkbook workbook = buildWorkbook(excelFileDesc.getTitleName(), excelFileDesc.getSheetName(), params.getExportBeanClass(), server, params);

                // 02 上传文件并更新任务下载地址
                uploadAndUpdateTask(workbook, fileName, params.getTaskId(), excelFileDesc.getCategory(), optUser);
            } catch (ExcelExportException e) {

                // 03 终止任务并更新任务状态为手动终止
                stopTaskByUser(taskId);
                log.info("exportBigExcel stopTaskByUser taskId:{} timeOut:{}", taskId, (System.currentTimeMillis() - start));

            } catch (Exception e) {
                log.error("exportBigExcel throw exception fileName: {} cost {}", excelFileDesc.getFileName(), (System.currentTimeMillis() - start), e);
                // 04 终止任务并更新任务为未知异常
                stopTaskByException(taskId, e);

            } finally {
                redisHandler.releaseLock(encryptStr, encryptStr);
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
        return (SXSSFWorkbook) ExcelExportUtil.exportBigExcel(params, pojoClass, server, queryParams);
    }

    /**
     * 检测导出数量
     */
    private void checkExportCount(Long exportCount, Long maxCount) {

        if (ObjectUtil.isNull(exportCount)) {
            return;
        }
        if (exportCount <= 0) {
            ExceptionUtils.fail("导出数据为空");
        }
        if (ObjectUtil.isNotNull(maxCount) && exportCount > maxCount) {
            ExceptionUtils.fail(String.format("据量超过限制条数(%s)，请添加筛选条件后重试", maxCount));
        }
    }

    /**
     * 上传文件后更新记录
     */
    private void uploadAndUpdateTask(SXSSFWorkbook workbook, String fileName, Long taskId, String category, String optUser) {

        String path = uploadFileHandler.uploadWordBook(workbook, fileName, excelExportProperties.getTargetPath(), category, optUser);
        excelExportHandler.updateTaskPath(taskId, path, getContext(taskId));
    }

    /**
     * 异常中断任务
     */
    private void stopTaskByException(Long taskId, Exception e) {
        excelExportHandler.updateTaskStatus(taskId, ExportStatusEnum.FAILURE.getStatus(), getContext(taskId), e);
    }

    /**
     * 手动中断任务
     */
    private void stopTaskByUser(Long taskId) {
        excelExportHandler.updateTaskStatus(taskId, ExportStatusEnum.USER_STOP.getStatus(), getContext(taskId), null);
    }

    /**
     * 构建一个任务工单
     *
     * @param taskId     工单唯一id
     * @param exportUser 导出人
     * @param fileName   文件名称
     */
    private void buildTaskInfo(Long taskId, String exportUser, ExcelExportParams<?, ?> params, ExcelFileDesc excelFileDesc, String fileName) {
        excelExportHandler.initTaskInfoAndSave(taskId, exportUser, params, excelFileDesc, fileName, getContext(taskId));
    }

    private ExportContext getContext(Long taskId) {
        return CONTEXT_CACHE.getIfPresent(taskId);
    }

}