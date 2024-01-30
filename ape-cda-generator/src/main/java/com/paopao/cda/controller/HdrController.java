package com.paopao.cda.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paopao.cda.domain.*;
import com.paopao.cda.service.CdaDictItemLydService;
import com.paopao.cda.service.HdrDictItemService;
import com.paopao.cda.utils.FileUtilCommon;
import com.paopao.cda.utils.SimilarUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/24
 */
@Slf4j
@RestController
@RequestMapping("hdrCda")
public class HdrController {

    @Autowired
    private HdrDictItemService hdrService;

    @Autowired
    private CdaDictItemLydService cdaDictItemLydService;

    /**
     * 匹配表 + 相似度匹配
     * @param dictCodeParam
     * @return
     */
    @PostMapping("find")
    public String findDifferentCode(@RequestBody DictCodeParam dictCodeParam) {
        StopWatch watch = new StopWatch();
        watch.start();
        String dictCode = dictCodeParam.getDictCode();
        String[] fieldArr = dictCodeParam.getFieldArr();
        String tableName = dictCodeParam.getTableName();
        String[] groupByParam = dictCodeParam.getGroupByParam();
        List<HdrDictItem> hdrDictItems = this.dictCodeTest(dictCode);
        List<ResponseData> dynamicList = this.dynamicTest(fieldArr, tableName, groupByParam);
        Set<HdrDictItem> hashSet = new HashSet<>(hdrDictItems);
        List<ResponseData> list = dynamicList.stream()
                .filter(b -> hashSet.stream()
                        .noneMatch(a -> {
                            if (b == null) {
                                return true;
                            }
                            String filed1 = b.getField1();
                            String filed2 = b.getField2();
                            return ObjectUtil.equal(filed1, a.getMatchCode()) && ObjectUtil.equal(filed2, a.getMatchName());
                        }))
                .collect(Collectors.toList());
//        System.out.println(list);
        log.info("过滤差值 time:{}s", watch.getTime(TimeUnit.SECONDS));
        StringBuilder sb = new StringBuilder();
        SimilarUtil<HdrDictItem> similarUtil = new SimilarUtil<>(hdrDictItems, HdrDictItem.class);
        for (ResponseData map : list) {
            String dictName = similarUtil.similarByCosine(map.getField1(), map.getField2());
            if (dictName == null) {
                continue;
            }
//            String dictName = this.similarByCosine(hdrDictItems, map.get("filed2"), HdrDictItem.class);
            sb.append(dictName)
//            sb.append("^")
                    .append("^");
            if (StrUtil.isNotEmpty(map.getField1())) {
                sb.append(map.getField1());
            }
            sb.append("^")
                    .append(map.getField2())
                    .append("\n");
        }
        sb.append("\n")
                .append(similarUtil.getLowVal());
        log.info("END time:{}s", watch.getTime(TimeUnit.SECONDS));
        watch.stop();
        return sb.toString();
    }

    /**
     * Excel文件读取 + 相似度匹配
     * @param dictCodeParam
     * @return
     */
    @PostMapping("find2")
    public String findOperation(@RequestBody DictCodeParam dictCodeParam) {
        StopWatch watch = new StopWatch();
        watch.start();
        String dictCode = dictCodeParam.getDictCode();
        String[] fieldArr = dictCodeParam.getFieldArr();
        String tableName = dictCodeParam.getTableName();
        String[] groupByParam = dictCodeParam.getGroupByParam();
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\疾病分类编码国标(2020版本）.xls";
        List<OperationData> hdrDictItems = new ArrayList<>();
        EasyExcel.read(fileName, OperationData.class, new PageReadListener<OperationData>(hdrDictItems::addAll))
                .sheet(1)
                .doRead();
        List<ResponseData> dynamicList = this.dynamicTest(fieldArr, tableName, groupByParam);
        StringBuilder sb = new StringBuilder();
        List<ResponseData> noMatchList = dynamicList.stream()
                .filter(a -> hdrDictItems.stream().noneMatch(v -> {
                    if (a == null) {
                        return true;
                    }
                    String filed1 = a.getField1();
                    String filed2 = a.getField2();
                    if (v.getItemName().equals(filed2)) {
                        String itemCode1 = v.getItemCode1();
                        String itemCode2 = v.getItemCode2();
                        sb.append(StrUtil.isEmpty(itemCode1) ? itemCode2 : itemCode1).append("^")
                                .append(v.getItemName()).append("^")
                                .append(filed1).append("^")
                                .append(filed2)
                                .append("\n");
                        return true;
                    }
                    return false;
                })).collect(Collectors.toList());
        log.info("first match time:{}s", watch.getTime(TimeUnit.SECONDS));
        sb.append("\n");
        SimilarUtil<OperationData> similarUtil = new SimilarUtil<>(hdrDictItems, OperationData.class);
        for (ResponseData map : noMatchList) {
            String dictName = similarUtil.similarByCosine(map.getField1(), map.getField2());
            if (dictName == null) {
                continue;
            }
            sb.append(dictName)
//            sb.append("^")
                    .append("^")
                    .append(map.getField1()).append("^")
                    .append(map.getField2())
                    .append("\n");
        }
        sb.append("\n")
                .append(similarUtil.getLowVal());
        log.info("END time:{}s", watch.getTime(TimeUnit.SECONDS));
        watch.stop();
        return sb.toString();
    }

    /**
     * 只读相似度文件
     * @param dictCode
     * @param fileName
     */
    @GetMapping("find3")
    public void findExcelByIllness(@RequestParam("dictCode") String dictCode,
                                @RequestParam(name = "fileName", required = false) String fileName) {
//        String dictCode = "GB/T 2261.2-2003";
//        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        this.supplyContent(dictCode, fileName);
    }

    @GetMapping("add")
    public void insertTemplate2(@RequestParam("dictCode") String dictCode,
                                @RequestParam(name = "fileName", required = false) String fileName) {
//        String dictCode = "GB/T 2261.2-2003";
//        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        this.supplyContent(dictCode, fileName);
    }

    @GetMapping("add2")
    public void insertTemplate(@RequestParam("dictCode") String dictCode,
                               @RequestParam(name = "fileName", required = false) String fileName) {
        List<String> strList = FileUtilCommon.readFile(fileName);
        List<CdaDictItemLyd> hdrDictItems = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            CdaDictItemLyd extracted = extracted(dictCode, strList, i);
            if (extracted != null) {
                hdrDictItems.add(extracted);
            }
        }
        cdaDictItemLydService.saveBatch(hdrDictItems);
        log.info("saveBatch size:{}", hdrDictItems.size());
    }

    private CdaDictItemLyd extracted(String dictCode, List<String> strList, int i) {
        String str = strList.get(i);
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\\^");
        if (split.length != 4) {
            log.error("line error data:{}", str);
            throw new IllegalArgumentException("异常");
        }
        CdaDictItemLyd hdrDictItem = new CdaDictItemLyd();
        hdrDictItem.setPk(dictCode + "|" + (i + 1));
        hdrDictItem.setDictCode(dictCode);
        hdrDictItem.setItemCode(split[0]);
        hdrDictItem.setItemValue(split[1]);
        if (StrUtil.isNotEmpty(split[2])) {
            hdrDictItem.setMatchCode(split[2]);
        }
        if (StrUtil.isNotEmpty(split[3])) {
            hdrDictItem.setMatchName(split[3]);
        }
        return hdrDictItem;
    }

    private void supplyContent(String dictCode, String fileName) {
        QueryWrapper<CdaDictItemLyd> wrapper = new QueryWrapper<>();
        wrapper.eq("DICT_CODE", dictCode);
        int count = cdaDictItemLydService.count(wrapper);
        if (count != 0) {
            System.out.println("当前存在 CODE 数据");
            return;
        }
        List<HdrDictItem> hdrDictItems = this.dictCodeTest(dictCode);
        List<CdaDictItemLyd> cdaDictItemLyds = new ArrayList<>();
        for (int i = 1; i <= hdrDictItems.size(); i++) {
            CdaDictItemLyd itemLyd = new CdaDictItemLyd();
            BeanUtils.copyProperties(hdrDictItems.get(i - 1), itemLyd);
            itemLyd.setPk(dictCode + "|" + i);
            cdaDictItemLyds.add(itemLyd);
        }
        if (StrUtil.isNotEmpty(fileName)) {
            this.opFileStr(fileName, dictCode, cdaDictItemLyds);
        }
//        System.out.println(cdaDictItemLyds);
        cdaDictItemLydService.saveBatch(cdaDictItemLyds);
        log.info("saveBatch size:{}", cdaDictItemLyds.size());
    }

    private void opFileStr(String fileName, String dictCode, List<CdaDictItemLyd> hdrDictItems) {
        List<String> strList = FileUtilCommon.readFile(fileName);
        for (int i = 0; i < strList.size(); i++) {
            int size = hdrDictItems.size() + 1;
            CdaDictItemLyd extracted = extracted(dictCode, strList, i);
            if (extracted != null) {
                extracted.setPk(dictCode + "|" + (size + 1));
                hdrDictItems.add(extracted);
            }
        }
    }

    public List<HdrDictItem> dictCodeTest(String dictCode) {
        return hdrService.selectByDictCode(dictCode);
    }

    public List<ResponseData> dynamicTest(String[] fieldArr, String tableName, String... groupByParam) {
        return hdrService.selectCode(fieldArr, tableName, groupByParam);
    }
}
