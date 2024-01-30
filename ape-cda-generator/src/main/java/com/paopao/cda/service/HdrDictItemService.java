package com.paopao.cda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.domain.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/23
 */
public interface HdrDictItemService extends IService<HdrDictItem> {

    public List<HdrDictItem> selectByDictCode(String dictCode);

    public List<ResponseData> selectCode(String[] fieldArr, String tableName, String... groupByParam);
}
