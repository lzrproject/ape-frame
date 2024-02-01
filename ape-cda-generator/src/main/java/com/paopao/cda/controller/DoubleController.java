package com.paopao.cda.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paopao.cda.domain.*;
import com.paopao.cda.service.CdaDictItemLydService;
import com.paopao.cda.service.HdrDictItemService;
import com.paopao.cda.utils.SimilarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/31
 */
@RestController
@RequestMapping("hdrSimilar")
public class DoubleController {

    @Autowired
    private CdaDictItemLydService cdaDictItemLydService;

    @Autowired
    private HdrDictItemService hdrService;

    @PostMapping("match")
    public String doubleMatch(@RequestBody DictCodeParam dictCodeParam) {
        String dictCode = dictCodeParam.getDictCode();
        String[] fieldArr = dictCodeParam.getFieldArr();
        String tableName = dictCodeParam.getTableName();
        String[] groupByParam = dictCodeParam.getGroupByParam();
        List<ResponseData> dynamicList = hdrService.selectCode(fieldArr, tableName, groupByParam);
        List<CdaDictItemLyd> extracted = extracted(dictCode);
        List<ResponseData> noMatch = getNoMatch(extracted, dynamicList);
        return this.getSimilar(extracted, noMatch);
    }

    private List<CdaDictItemLyd> extracted(String dictCode) {
        QueryWrapper<CdaDictItemLyd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DICT_CODE", dictCode);
        return cdaDictItemLydService.list(queryWrapper);
    }

    private List<ResponseData> getNoMatch(List<CdaDictItemLyd> list, List<ResponseData> dynamicList) {
        return dynamicList.stream().filter(c -> {
            if (c == null || StrUtil.isAllEmpty(c.getField1(), c.getField2())) {
                return false;
            }
            return list.stream().noneMatch(b -> {
                String filed1 = c.getField1();
                String filed2 = c.getField2();
                return ObjectUtil.equal(filed1, b.getMatchCode()) && ObjectUtil.equal(filed2, b.getMatchName());
            });
        }).collect(Collectors.toList());
    }

    private String getSimilar(List<CdaDictItemLyd> list, List<ResponseData> a) {
        if (CollectionUtils.isEmpty(a)) {
            return null;
        }
        SimilarUtil<CdaDictItemLyd> similarUtil = new SimilarUtil<>(list, CdaDictItemLyd.class);
        StringBuilder sb = new StringBuilder();
        for (ResponseData str : a) {
            String dictName = similarUtil.similarByCosine(str.getField1(), str.getField2());
            if (dictName == null) {
                continue;
            }
            sb.append(dictName)
//            sb.append("^")
                    .append("^")
                    .append(str.getField1()).append("^")
                    .append(str.getField2())
                    .append("\n");
        }
        sb.append("\n")
                .append(similarUtil.getLowVal());
        return sb.toString();
    }

    public static void main(String[] args) {
        HdrDictItem hdrDictItem = new HdrDictItem();
        CdaDictItemLyd cdaDictItemLyd = new CdaDictItemLyd();
        System.out.println(hdrDictItem instanceof Dict);
        System.out.println(hdrDictItem instanceof Dict);
    }
}
