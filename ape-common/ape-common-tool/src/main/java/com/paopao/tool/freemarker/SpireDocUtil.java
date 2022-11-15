package com.paopao.tool.freemarker;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 * @Author paoPao
 * @Date 2022/11/15
 * @Description
 */
public class SpireDocUtil {

    public static void main(String[] args) {
        try {
            Document document = new Document();
            // 加载文件路径
            document.loadFromFile("D:\\idea\\typoraTest\\新建 Microsoft Word 文档.docx");
            document.saveToFile("D:\\idea\\typoraTest\\新建 Microsoft Word 文档.xml", FileFormat.Word_Xml);
            document.dispose();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
