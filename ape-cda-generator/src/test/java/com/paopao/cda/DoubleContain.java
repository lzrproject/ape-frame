package com.paopao.cda;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paopao.cda.config.MybatisPlusConfig;
import com.paopao.cda.domain.CdaDictItemLyd;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.domain.OperationData;
import com.paopao.cda.domain.ResponseData;
import com.paopao.cda.service.CdaDictItemLydService;
import com.paopao.cda.service.HdrDictItemService;
import com.paopao.cda.utils.FileUtilCommon;
import com.paopao.cda.utils.SimilarUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/30
 */
@SpringBootTest(classes = {HdrApplication.class, MybatisPlusConfig.class})
public class DoubleContain {

    @Autowired
    private CdaDictItemLydService cdaDictItemLydService;

    @Autowired
    private HdrDictItemService hdrService;

    @Test
    public void test() {
        String[] a = {
                "普食  ",
                "全流食  ",
                "禁食  ",
                "治疗饮食",
                "半流食  "
        };
        QueryWrapper<CdaDictItemLyd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DICT_CODE", "CV06.00.224");
        List<CdaDictItemLyd> list = cdaDictItemLydService.list(queryWrapper);
        List<String> noMatch = getNoMatch(list, a);
        System.out.println(this.getSimilar(list, noMatch));
    }

    private List<String> getNoMatch(List<CdaDictItemLyd> list, String[] a) {
        return Arrays.asList(a).stream().filter(c -> {
            return list.stream().noneMatch(b -> {
                return b.getMatchName().contains(c) && b.getMatchCode().equals(c);
            });
        }).collect(Collectors.toList());
    }

    private String getSimilar(List<CdaDictItemLyd> list, List<String> a) {
        SimilarUtil<CdaDictItemLyd> similarUtil = new SimilarUtil<>(list, CdaDictItemLyd.class);
        StringBuilder sb = new StringBuilder();
        for (String str : a) {
            String dictName = similarUtil.similarByCosine(str, str);
            if (dictName == null) {
                continue;
            }
            sb.append(dictName)
//            sb.append("^")
                    .append("^")
                    .append(str).append("^")
                    .append(str)
                    .append("\n");
        }
        sb.append("\n")
                .append(similarUtil.getLowVal());
        return sb.toString();
    }

    @Test
    public void add() {
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        String dictCode = "ICD-9-CM-3";
        List<String> strList = FileUtilCommon.readFile(fileName);
        List<CdaDictItemLyd> cdaDictItemLyds = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            CdaDictItemLyd extracted = this.extracted(dictCode, strList, i);
            if (extracted != null) {
                extracted.setPk(dictCode + "|" + (1 + i));
                cdaDictItemLyds.add(extracted);
            }

        }
        cdaDictItemLydService.saveBatch(cdaDictItemLyds);
        System.out.println("save " + cdaDictItemLyds.size());
    }

    private CdaDictItemLyd extracted(String dictCode, List<String> strList, int i) {
        String str = strList.get(i);
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\\^");
        if (split.length != 4) {
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

    @Test
    public void test2() {
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        String dictCode = "ICD-9-CM-3";
        List<String> strList = FileUtilCommon.readFile(fileName);
        List<HdrDictItem> hdrDictItems = hdrService.selectByDictCode(dictCode);
        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            String[] split = str.split("\\^");
            String code = split[2];
            String name = split[3];
            HdrDictItem hdrDictItem = hdrDictItems.stream().filter(v -> {
                String matchName = v.getMatchName();
                return name.equals(matchName);
            }).findAny().get();
            sb.append(hdrDictItem.getItemCode())
                    .append("^")
                    .append(hdrDictItem.getItemValue()).append("^")
                    .append(code).append("^")
                    .append(name).append("\n");
        }
        System.out.println(sb);
    }

}
