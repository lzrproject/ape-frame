package com.paopao.cda.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paopao.cda.config.DynamicTableHandle;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.domain.ResponseData;
import com.paopao.cda.mapper.DynamicMapper;
import com.paopao.cda.mapper.HdrDictItemMapper;
import com.paopao.cda.service.HdrDictItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/23
 */
@Service
public class HdrDictItemServiceImpl extends ServiceImpl<HdrDictItemMapper, HdrDictItem> implements HdrDictItemService {


    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    @DS("DICTITEM")
    public List<HdrDictItem> selectByDictCode(String dictCode) {
        QueryWrapper<HdrDictItem> wrapper = new QueryWrapper<>();
        wrapper.eq("DICT_CODE", dictCode);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    @DS("CDAVIEW")
    public List<ResponseData> selectCode(String[] fieldArr, String tableName, String... groupByParam) {
        DynamicTableHandle.setThreadLocal(tableName);
        StringBuilder fields = new StringBuilder();
        int i = 1;
        for (String str : fieldArr) {
            fields.append(str).append(" as field")
                    .append(i).append(",");
            i++;
        }
        fields.deleteCharAt(fields.length() - 1);
        String sql = "select " + fields.toString() + " from " + tableName;
        sql += " group by " + StringUtils.join(groupByParam, ",");
        return dynamicMapper.selectByCondition(sql);
    }
}
