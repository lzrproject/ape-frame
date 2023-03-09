package com.paopao.tool.typora;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 效果：将源目录所有md文件内容中tihuan配置内容替换为tihuanTO内容，最终输出到指定路径。
 * 配置说明：填写四个配置即可
 * COPY_ORIGN：源目录
 * COPY_ORIGN：目标输出目录(可填自己源目录)
 * tihuan：被替换内容
 * tihuanTO：替换内容
 * <p>
 * 说明：想要将md文件中的1替换为2，那么被替换内容就是1、替换内容就是2
 *
 * @Author paoPao
 * @Date 2022/11/3
 * @Description 批量替换MD图片路径工具
 */
public class BatchReplaceUtil {

    private static Logger log = Logger.getLogger("com.test.typora");
    // 目标目录
//    private static String TARGET_DIR = "E:/idea/MyStudy/备份/文档";
    private static String TARGET_DIR = "D:/idea/备份/aa";
    // 新存储目录
//    private static String NEWEST_DIR = "E:/idea/MyStudy/备份";
    private static String NEWEST_DIR = "D:/idea/备份";
    // 替换内容
    private static String REPLACE_TO = "images/";

    public static void main(String[] args) throws Exception {

        //存储md文件绝对路径
        Map<String, String> filesPathMap = new HashMap<>();
        long start = System.currentTimeMillis();
        Files.walk(Paths.get(TARGET_DIR)).forEach(path -> {
            if (!path.toFile().exists()) {
                return;
            }
            String targetFileName = path.toFile().getName();
            // .md || .MD
            String lowerFileName = targetFileName.toLowerCase(Locale.ROOT);
            if (!path.toFile().isFile() || !lowerFileName.endsWith(".md")) {
                return;
            }
            // 绝对路径
            String absolutePath = path.toFile().getAbsolutePath();
            filesPathMap.put(targetFileName, absolutePath);
        });
        doWork(filesPathMap);
        log.info("目标文件：" + filesPathMap);
        long end = System.currentTimeMillis();
        System.out.println("文件处理完成，耗费时间：" + String.format("%.2f", (end - start) / 1000.0));
    }

    /**
     * @Description
     * @Author paoPao
     * @Date 2022/11/3
     */
    private static void doWork(Map<String, String> filesPathMap) throws IOException {
        filesPathMap.forEach((key, value) -> {
            try {
                //读取一个文件，获取到内容
                String content = readFile(value);
                if (content == null) return;
                String newContent = handleTypeOne(key, content);
                writeFile(newContent, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @Description 写入文件
     * @Author paoPao
     * @Date 2022/11/3
     */
    private static void writeFile(String newContent, String targetFile) throws IOException {
        File file = new File(NEWEST_DIR + "/" + targetFile);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        osw.write(newContent);
        osw.flush();
    }

    /**
     * @Description 读取文件
     * @Author paoPao
     * @Date 2022/11/3
     */
    private static String readFile(String targetPath) throws IOException {
        FileInputStream fis = new FileInputStream(targetPath);
        final InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String content = "";
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            content += line + "\r\n";
        }
        return content;
    }

    ////////////////////////////////
    //匹配: ![]() 语法的图片
    private static String handleTypeOne(String fileName, String content) {
        String reg = "!\\[.*?]\\(.*?\\)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String srcPath = matcher.group();
            int start = srcPath.indexOf("(");
            int lastIndexOf = srcPath.lastIndexOf(")");
            // 需要替换的路径
            srcPath = srcPath.substring(start + 1, lastIndexOf).trim();
            if ("".equals(srcPath)) continue;
            int index = srcPath.lastIndexOf("/");
            if (index == -1) {
                index = srcPath.lastIndexOf("\\");
            }
            String imgName = srcPath.substring(index + 1);
            String targetPath = REPLACE_TO + imgName;
            if (!isAccess(targetPath)) {
                log.warning("文件[" + fileName + "]:{" + targetPath + "}图片不存在");
                continue;
            }

            content = content.replaceAll(replace(srcPath), replace(targetPath));
        }
        return content;
    }

    /**
     * @Description 拼接的地址是否存在
     * @Author paoPao
     * @Date 2022/11/3
     */
    public static boolean isAccess(String imgUrl) {
        try {
            String filePath = NEWEST_DIR + "/" + imgUrl;
            File file = new File(filePath);
            if (file.exists()) {
                return true;
            }
//            URL url = new URL("file://" + );
//            URLConnection uc = url.openConnection();
//            InputStream in = uc.getInputStream();
//            if (imgUrl.equalsIgnoreCase(uc.getURL().toString()))
//                in.close();

        } catch (Exception e) {
            log.info("[BatchReplaceUtil.isAccess]异常：" + e.getMessage());
        }
        return false;
    }

    /**
     * @Description 特殊字符加上转义符号
     * @Param filePath images/]P2R93VFHM[U_BDQQGFHP5.jpg
     * @Author paoPao
     * @Date 2022/11/3
     */
    private static String replace(String filePath) {
        int index = filePath.lastIndexOf("/");
        if (index == -1) {
            index = filePath.lastIndexOf("\\");
        }
        String fileName = filePath.substring(index);
        StringBuffer sb = new StringBuffer();
        char[] ch = fileName.toCharArray();
        for (char c : ch) {
            if (c == '[') {
                sb.append("\\[");
            } else if (c == ']') {
                sb.append("\\]");
            } else if (c == '\\') {
                sb.append("\\\\");
            } else {
                sb.append(c);
            }
        }
        return filePath.substring(0, index).replaceAll("\\\\", "\\\\\\\\") + sb.toString();
    }


    ///////////////////////////////////////

    /**
     * @Description 将远程文件下载到本地
     * @Author paoPao
     * @Date 2022/11/3
     */
    public static void getPictureUrl() {

    }
}
