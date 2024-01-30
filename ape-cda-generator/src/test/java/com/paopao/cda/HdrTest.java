package com.paopao.cda;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paopao.cda.config.MybatisPlusConfig;
import com.paopao.cda.domain.CdaDictItemLyd;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.service.CdaDictItemLydService;
import com.paopao.cda.service.HdrDictItemService;
import com.paopao.cda.utils.FileUtilCommon;
import com.paopao.cda.utils.SimHashUtil;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/23
 */
@SpringBootTest(classes = {HdrApplication.class, MybatisPlusConfig.class})
public class HdrTest {

    @Autowired
    private HdrDictItemService hdrService;

    @Autowired
    private CdaDictItemLydService cdaDictItemLydService;

    @Test
    public void test() {
        String dictCode = "GB/T 2261.4-2003";
        String[] fieldArr = new String[]{"SUBSTRING_INDEX( `ST_OCCUPATION`, _UTF8MB4 '#,#', 1 )",
                "SUBSTRING_INDEX( `ST_OCCUPATION`, _UTF8MB4 '#,#', - 1 )"};
        String tableName = "JHVIEW.V_JH_PAT_INFO_CDA";
        String[] groupByParam = new String[]{"SUBSTRING_INDEX( `ST_OCCUPATION`, _UTF8MB4 '#,#', 1 )",
                "SUBSTRING_INDEX( `ST_OCCUPATION`, _UTF8MB4 '#,#', - 1 )"};
        List<HdrDictItem> hdrDictItems = this.dictCodeTest(dictCode);
        List<Map<String, String>> dynamicList = this.dynamicTest(fieldArr, tableName, groupByParam);
        Set<HdrDictItem> hashSet = new HashSet<>(hdrDictItems);
        List<Map<String, String>> list = dynamicList.stream()
                .filter(b -> hashSet.stream()
                        .noneMatch(a -> {
                            if (CollectionUtils.isEmpty(b)) {
                                return true;
                            }
                            String filed1 = b.get("filed1");
                            String filed2 = b.get("filed2");
                            return ObjectUtil.equal(filed1, a.getMatchCode()) && ObjectUtil.equal(filed2, a.getMatchName());
                        }))
                .collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void insertTest() {
        String dictCode = "GB/T 2261.2-2003";
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        this.supplyContent(dictCode, fileName);
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
        if (fileName.length() != 0) {
            this.opFileStr(fileName, dictCode, cdaDictItemLyds);
        }
        System.out.println(cdaDictItemLyds);
        cdaDictItemLydService.saveBatch(cdaDictItemLyds);

    }

    private void opFileStr(String fileName, String dictCode, List<CdaDictItemLyd> hdrDictItems) {
        List<String> strList = FileUtilCommon.readFile(fileName);
        strList.forEach(v -> {
            int size = hdrDictItems.size() + 1;
            String[] split = v.split("\\^");
            CdaDictItemLyd hdrDictItem = new CdaDictItemLyd();
            hdrDictItem.setPk(dictCode + "|" + size);
            hdrDictItem.setDictCode(dictCode);
            hdrDictItem.setItemCode(split[0]);
            hdrDictItem.setItemValue(split[1]);
            if (StrUtil.isNotEmpty(split[2])) {
                hdrDictItem.setMatchCode(split[2]);
            } else if (StrUtil.isNotEmpty(split[3])) {
                hdrDictItem.setMatchName(split[3]);
            }

            hdrDictItems.add(hdrDictItem);
        });
    }

    public List<HdrDictItem> dictCodeTest(String dictCode) {
        return hdrService.selectByDictCode(dictCode);
    }

    public List<Map<String, String>> dynamicTest(String[] fieldArr, String tableName, String... groupByParam) {
//        return hdrService.selectCode(fieldArr, tableName, groupByParam);
        return null;
    }


    @Test
    public void similarTest() {
        String dictCode = "ICD-9-CM-3";
        List<HdrDictItem> itemList = this.dictCodeTest(dictCode);
        // 原始内容
        String originalTitle = "经耳内镜咽鼓管扩张术";
        Map<CharSequence, Integer> map1 = Arrays.stream(originalTitle.split(""))
                .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
        Map<String, Double> simHashMap = new HashMap<>(itemList.size(), 0.75F);
        CosineSimilarity similarity = new CosineSimilarity();
        // 计算相似度
        itemList.forEach(v -> {
            Map<CharSequence, Integer> map2 = Arrays.stream(v.getMatchName().split(""))
                    .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
            Double similar = similarity.cosineSimilarity(map1, map2);
            simHashMap.put(v.getMatchName(), similar);
        });
        // 按相标题内容排序输出控制台
//        System.out.println("原始标题：" + originalTitle);
//        System.out.println("-------------------------------------");
//        for (Map.Entry<String, Double> entry : simHashMap.entrySet()) {
//            System.out.println("标题：" + entry.getKey() + "-----------相似度：" + entry.getValue());
//        }
        this.sortMap(simHashMap);
    }


    private void sortMap(Map<String, Double> map) {
        Optional<Map.Entry<String, Double>> maxEntry = map.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue));
        String maxKey = maxEntry.get().getKey();
        double maxValue = maxEntry.get().getValue();
        System.out.println(maxKey + " " + maxValue);
    }
}
