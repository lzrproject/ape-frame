package com.paopao.excel.core.csv;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.paopao.excel.core.ExcelExportParams;
import com.paopao.excel.core.interfaces.SearchExcelDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * csv StatefulBeanToCsvBuilder 构建
 *
 * @Author paoPao
 * @Date 2023/11/8
 */
@Slf4j
@SuppressWarnings("all")
public class CsvExportBuild {

    Writer writer = null;
    StatefulBeanToCsv beanToCsv = null;

    private static final String GBK_CHARSET = "GBK";

    public static void exportBigCsv(String exportPath, ExcelExportParams queryParams, String fileName) {
        CsvExportBuild build = new CsvExportBuild();
        build.initOpenCsv(exportPath, queryParams.getExportBeanClass(), fileName);
        build.exportBigCsv(queryParams);
    }

    private void initOpenCsv(String exportPath, Class<?> pojoClass, String fileName) {
        try {
//            String fileName = "yourfile.csv";
            File file = new File(exportPath + "/");
            if (!file.exists() && !file.isDirectory()) {
                // 创建多级目录
                file.mkdirs();
            }
            HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
            strategy.setType(pojoClass);

            writer = new OutputStreamWriter(new FileOutputStream(exportPath + "/" + fileName), GBK_CHARSET);
            beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(strategy)
                    .build();
        } catch (Exception e) {
            log.error("initOpenCsv error", e);
        }
    }

    private void exportBigCsv(ExcelExportParams queryParams) {
        int page = 1;
        SearchExcelDataService server = queryParams.getExportServer();
        List<Object> list = server.selectListForExcelExport(queryParams, page++);
        try {
            while (list != null && list.size() > 0) {
                beanToCsv.write(list);
                list = server.selectListForExcelExport(queryParams, page++);
            }
        } catch (Exception e) {
            log.error("exportBigCsv error", e);
        } finally {
            this.closeExportBigCsv();
        }
    }

    private void closeExportBigCsv() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            log.error("closeExportBigCsv error", e);
        }
    }
}
