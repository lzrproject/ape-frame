package com.paopao.generator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class GeneratorUtil {

    @Value("${entity.home}")
    private String home;

    @Value("${entity.package}")
    private String setParent;

    @Value("${entity.author}")
    private String author;

    public void create(String modelName, String tablePrefix, String url, String driverClass, String username, String password, String... tableName) {
        log.info("modelName：{}，tableName：{}，url：{}，driverClass：{}", modelName, tableName, url, driverClass);
        if (StringUtils.isBlank(home)) {
            log.info("读取文件路径失败！");
            return;
        }

        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String homePath = home + "/" + modelName;
//        String homePath = System.getProperty("user.dir");   //父模块路径
//        String projectName = "/mybatis-plus-template";
        String projectPath = homePath + "/src/main/java/";    //当前模块
//        String setParent = "cn.ubattery.demo";

        gc.setOutputDir(projectPath);   //生成文件输出根目录
        gc.setAuthor(author);
        gc.setOpen(false);  // 生成代码后是否打开文件
        gc.setFileOverride(false);// 是否覆盖原文件
        //gc.setActiveRecord(true);// 开启 activeRecord 模式
//        gc.setEnableCache(false);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(true);// XML columList
//        gc.setSwagger2(true); //实体属性 Swagger2 注解
//        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE); //注释的日期格式只显示时间
        gc.setMapperName("%sMapper");// 去掉生成的Mapper文件前缀
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sService");
        gc.setControllerName("%sController");
        gc.setEntityName("%sDTO");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverClass);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dsc);

        Map<String, String> map = new HashMap<>();
        map.put("modelName", modelName);
        map.put("packageName", setParent);
        map.put("driverClass", driverClass);
        map.put("url", url);
        map.put("username", username);
        map.put("password", password);

        // 包配置（可修改）
        PackageConfig pc = new PackageConfig();
//        pc.setPathInfo(map);
//        pc.setModuleName(modelName);
        pc.setParent(setParent);
//        pc.setEntity("entity");
//        pc.setMapper("mapper");
////        pc.setXml("mapper.xml");
//        pc.setService("service");
//        pc.setController("controller");
//        pc.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        log.info("生成路径：{}", projectPath + setParent);
        //自定义模板配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
//        fileOutConfigs.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名及地址 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return homePath+"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        fileOutConfigs.add(new FileOutConfig("/template/controller.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath+ setParent+ "/controller/" + tableInfo.getControllerName() + StringPool.DOT_JAVA;
//            }
//        });
//        fileOutConfigs.add(new FileOutConfig("/template/serviceImpl.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath+ setParent+ "/service/" + tableInfo.getServiceName() + StringPool.DOT_JAVA;
//            }
//        });
        fileOutConfigs.add(new FileOutConfig("/template/serviceImpl.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + setParent.replaceAll("\\.","/") + "/service/" + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigs.add(new FileOutConfig("/template/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + setParent.replaceAll("\\.","/") + "/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        fileOutConfigs.add(new FileOutConfig("/template/mapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + setParent.replaceAll("\\.","/") + "/mapper/" + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(cfg);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig
                .setEntity(null)
                .setXml(null)
                .setMapper(null)
                .setService(null)
                .setServiceImpl(null)
                .setController(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//开启实体类Lombok注解

        strategy.setRestControllerStyle(true);//生成Rest风格controller
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setEntityTableFieldAnnotationEnable(true); //生成@TableField
        strategy.setInclude(tableName);// 需要生成的表
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(false);
        if (tablePrefix != null)
            strategy.setTablePrefix(tablePrefix); //去掉表前缀

        // 自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        autoGenerator.setStrategy(strategy);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }


}
