package com.paopao.demo.controller;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.paopao.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.ext.GBK;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/11/3
 */
@RestController
@RequestMapping("csv")
@Slf4j
public class CsvController {

    @Value("${paopao.export.excel.targetPath}")
    private String exportPath;

    public static final String GBK_CHARSET = "GBK";

    @GetMapping("test")
    public void exportToCsv() {
        Writer writer = null;
        ICSVWriter icsvWriter = null;
        StatefulBeanToCsv<User> beanToCsv = null;
        try {
            String fileName = "yourfile.csv";
            File file = new File(exportPath + "/");
            if (!file.exists() && !file.isDirectory()) {
                // 创建多级目录
                file.mkdirs();
            }
//            new FileWriter(file,)
//            writer = new FileWriter(exportPath + "/" + fileName);
            writer = new OutputStreamWriter(new FileOutputStream(exportPath + "/" + fileName), GBK_CHARSET);
            beanToCsv = new StatefulBeanToCsvBuilder<User>(writer)
                    .build();

            List<User> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setUserid((long) i);
                user.setUsername("泡泡" + i);
                list.add(user);
            }
            beanToCsv.write(list);
            beanToCsv.write(list);
//            icsvWriter = new CSVWriterBuilder(writer)
////                    .withSeparator('\t')
//                    .build();
//            String[] entries = "first#second#third".split("#");
//            List<String[]> csv = new ArrayList<>();
//            csv.add(entries);
//            icsvWriter.writeNext(entries, false);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (icsvWriter != null) {
                    icsvWriter.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
