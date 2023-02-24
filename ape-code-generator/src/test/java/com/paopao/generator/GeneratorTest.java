package com.paopao.generator;

import com.paopao.generator.controller.CodeController;
import com.paopao.generator.entity.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        Param param = new Param();
        param.setModelName("jhmrdr");
        param.setTableName("JHEMR_cda01");
        param.setDriverClass("com.mysql.cj.jdbc.Driver");
        param.setUrl("jdbc:mysql://10.240.246.207:4000/JHDL?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&tinyInt1isBit=false&allowMultiQueries=true&sessionVariables=group_concat_max_len=1024000");
        param.setUsername("root");
        param.setPassword("jhmk5354(#($");
        codeController.create(param);
    }

    @Test
    public void test() throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        String a = "";
        System.out.println(fmt.parse(a));
    }
}
