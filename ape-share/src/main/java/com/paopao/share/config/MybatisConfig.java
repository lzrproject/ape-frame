package com.paopao.share.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.paopao.share.config.dynamicDataSourceConfig.DynamicDataSource;
import com.paopao.share.config.dynamicDataSourceConfig.DatabaseType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description mybatis配置类
 */
@Configuration
@MapperScan(basePackages = "com.paopao.share.mapper", sqlSessionFactoryRef = "sessionFactory")
public class MybatisConfig {

    /**
     * @Description: 设置动态数据源
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier("test1DataSource") DataSource test1DataSource,
                                        @Qualifier("test2DataSource") DataSource test2DataSource,
                                        @Qualifier("test3DataSource") DataSource test3DataSource) {

        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DatabaseType.test1, test1DataSource);
        targetDataSource.put(DatabaseType.test2, test2DataSource);
        targetDataSource.put(DatabaseType.test3, test3DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(test1DataSource);
        return dataSource;
    }

    /**
     * @Description: 根据动态数据源创建sessionFactory
     */
    @Bean(name = "sessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("test1DataSource") DataSource test1DataSource,
                                            @Qualifier("test2DataSource") DataSource test2DataSource,
                                            @Qualifier("test3DataSource") DataSource test3DataSource) throws Exception{
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        //构造方法，解决动态数据源循环依赖问题。
        sessionFactoryBean.setDataSource(this.dataSource(test1DataSource,test2DataSource, test3DataSource));
        return sessionFactoryBean.getObject();

    }

}
