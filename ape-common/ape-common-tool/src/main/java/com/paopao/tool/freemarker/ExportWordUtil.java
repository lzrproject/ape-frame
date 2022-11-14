package com.paopao.tool.freemarker;

import com.paopao.tool.spring.SpringContextUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author: paoPao
 * @Date: 2022/11/13
 * @Description: 导出word的util
 */
public class ExportWordUtil {
    private static Configuration configuration = null;

    public static final String SUFFIX = ".doc";

    static {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        // tle模板路径
        configuration.setClassForTemplateLoading(ExportWordUtil.class,"/template/word");
    }

    public static void exportWord(Map map, String title, String ftlName) {
        File file = null;
        InputStream inputStream = null;
        try {
            // 获取模板
            Template template = configuration.getTemplate(ftlName);
            file = createDocFile(map,template);
            inputStream = new FileInputStream(file);
            // 文件名
            String fileName = title + SUFFIX;
            HttpServletResponse response = SpringContextUtils.getHttpServletResponse();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            byte[] buffer = new byte[512];
            int

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createDocFile(Map dataMap, Template template) {
        File file = new File("init.doc");
        Writer streamWriter = null;
        try {
            streamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            // 导入文件
            template.process(dataMap, streamWriter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (streamWriter != null) {
                try {
                    streamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
