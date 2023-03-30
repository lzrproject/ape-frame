package com.paopao.generator;

import com.paopao.generator.controller.CodeController;
import com.paopao.generator.entity.Param;
import com.paopao.generator.util.GeneratorUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author paoPao
 * @Date 2023/2/24
 * @Description:
 */
@SpringBootTest
public class GeneratorTest {
    @Autowired
    private CodeController codeController;

    @Test
    public void createTemplate() {
        List<String> tableName = new ArrayList<>();
//        tableName.add("JHEMR_cda01");
        tableName.add("JHEMR_cda02");
        tableName.add("JHEMR_cda03");
        tableName.add("JHEMR_cda09");
        tableName.add("JHEMR_cda26");
        tableName.add("JHEMR_cda27");
        tableName.add("JHEMR_cda28");
        tableName.add("JHEMR_cda29");
        tableName.add("JHEMR_cda30");
        tableName.add("JHEMR_cda31");
        tableName.add("JHEMR_cda32");
        tableName.add("JHEMR_cda33");
        tableName.add("JHEMR_cda34");
        tableName.add("JHEMR_cda35");
//        tableName.add("JHEMR_cda39");
        Param param = new Param();
        param.setModelName("jhmrdr");
        param.setTableName(tableName.toArray(new String[]{}));
        param.setDriverClass("com.mysql.cj.jdbc.Driver");
        param.setUrl("jdbc:mysql://10.240.246.207:4000/JHDL?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&tinyInt1isBit=false&allowMultiQueries=true&sessionVariables=group_concat_max_len=1024000");
        param.setUsername("root");
        param.setPassword("jhmk5354(#($");
        codeController.create(param);
    }

    @Test
    public void test() throws ParseException {
//        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
//        String a = "";
//        System.out.println(fmt.parse(a));
        System.out.println(splitStr("岁").get(1));
    }

    // 分割字符
    public static List<String> splitStr(String ageStr) {
        List<String> list = new ArrayList<>();
        String regex = "[0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ageStr.trim());
        int index = 0;
        if (matcher.find()) {
            String value = matcher.group(0);
            // 存value
            list.add(value);
            index = value.length();
        }
        // 存name
        list.add(ageStr.substring(index));
        return list;
    }
}
