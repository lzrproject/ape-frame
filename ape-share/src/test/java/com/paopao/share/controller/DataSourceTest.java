package com.paopao.share.controller;

import com.paopao.share.ShareApplication;
import com.paopao.share.mapper.UserMapper;
import com.paopao.share.utils.DynamicDataSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author paoPao
 * @Date 2023/2/8
 * @Description 动态数据源测试
 */
@SpringBootTest(classes = {ShareApplication.class})
@Slf4j
public class DataSourceTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDynamicDatasource() {
        System.out.println("=====默认数据源Test1=====");
        int countTest1 = userMapper.getCount();
        System.out.println("数据库的数据量：" + countTest1);
        System.out.println("=====切换数据源Test2=====");
        DynamicDataSourceUtils.chooseBasicDataSource();
        int countTest2 = userMapper.getCount();
        System.out.println("数据库的数据量：" + countTest2);
        System.out.println("=====切换数据源Test3=====");
        DynamicDataSourceUtils.chooseBranchDataSource();
        int countTest3 = userMapper.getCount();
        System.out.println("数据库的数据量：" + countTest3);
    }
}
