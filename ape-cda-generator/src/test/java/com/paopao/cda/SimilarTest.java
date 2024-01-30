package com.paopao.cda;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.domain.OperationData;
import com.paopao.cda.utils.SimHashUtil;
import com.paopao.cda.utils.SimilarUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/25
 */

public class SimilarTest {

    @Test
    public void test() {
        // 准备测试标题内容数据
        List<String> titleList = new ArrayList<>();
        titleList.add("有哪些养猫必须知道的冷知识");
        titleList.add("有哪些养猫必须知道的冷");
        titleList.add("有哪些养猫必须知道");
        titleList.add("有哪些养猫");
        titleList.add("有哪些");

        // 原始标题内容数据
        String originalTitle = "有哪些养猫必须知道的冷知识？";

        Map<String, Double> simHashMap = new HashMap<>(16, 0.75F);

        System.out.println("======================================");
        long startTime = System.currentTimeMillis();
        System.out.println("原始标题：" + originalTitle);

        // 计算相似度
        titleList.forEach(title -> {
            SimHashUtil mySimHash_1 = new SimHashUtil(title, 64);
            SimHashUtil mySimHash_2 = new SimHashUtil(originalTitle, 64);

            Double similar = mySimHash_1.getSimilar(mySimHash_2);

            simHashMap.put(title, similar);
        });

        // 打印测试结果到控制台
        /* simHashMap.forEach((title, similarity) -> {
            System.out.println("标题："+title+"-----------相似度："+similarity);
        });*/

        // 按相标题内容排序输出控制台
        Set<String> titleSet = simHashMap.keySet();
        Object[] titleArrays = titleSet.toArray();
        Arrays.sort(titleArrays, Collections.reverseOrder());

        System.out.println("-------------------------------------");
        for (Object title : titleArrays) {
            System.out.println("标题：" + title + "-----------相似度：" + simHashMap.get(title));
        }

        // 求得运算时长（单位：毫秒）
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("\n本次运算总耗时" + totalTime + "毫秒");

        System.out.println("======================================");
    }

    @Test
    public void cosineTest() {
        String s1 = "台湾省";
        String s2 = "台湾";
        Map<CharSequence, Integer> map1 = Arrays.stream(s1.split(""))
                .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
        Map<CharSequence, Integer> map2 = Arrays.stream(s2.split(""))
                .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
        CosineSimilarity similarity = new CosineSimilarity();
        System.out.println(similarity.cosineSimilarity(map1, map2));
    }

    @Test
    public void jaccardTest() {
        String s1 = "台湾省";
        String s2 = "台湾";
        JaccardSimilarity similarity = new JaccardSimilarity();
        System.out.println(similarity.apply(s1, s2));
    }

    @Test
    public void levenshteinTest() {
        String s1 = "桡骨和尺骨使用外固定装置";
        String s2 = "桡骨外固定术";
        LevenshteinResults apply = LevenshteinDetailedDistance.getDefaultInstance().apply(s1, s2);
        Integer distance = apply.getDistance();
        double v = (double) distance / s1.length();
        System.out.println(v);
    }

    @Test
    public void similarByExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<OperationData> dictList = this.getOperationData();
        List<HdrDictItem> templateCode = this.getHdrDictItem();
        SimilarUtil<OperationData> similarUtil = new SimilarUtil<>(dictList, OperationData.class);
        StringBuilder sb = new StringBuilder();
        templateCode.forEach(v -> {
            String dictName = similarUtil.similarByCosine(v.getMatchCode(), v.getMatchName());
            if (dictName != null) {
                sb.append(dictName)
//            sb.append("^")
                        .append("^");
                if (StrUtil.isNotEmpty(v.getMatchCode())) {
                    sb.append(v.getMatchCode());
                }
                sb.append("^")
                        .append(v.getMatchName())
                        .append("\n");
            }
        });
        sb.append("\n")
                .append(similarUtil.getLowVal());
        stopWatch.stop();
        System.out.println("size:" + templateCode.size()
                + " costTime:" + stopWatch.getTime(TimeUnit.SECONDS)
                + "data:\n" + sb.toString());
    }

    private List<OperationData> getOperationData() {
        String chinaExcel = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\《中医病证分类与代码》字典.xlsx";
        String westernExcel = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\疾病分类编码国标(2020版本）.xls";
//        OperationData operationData = new OperationData();
        List<OperationData> list = new ArrayList<>();
        EasyExcel.read(westernExcel, OperationData.class, new PageReadListener<OperationData>(list::addAll))
                .sheet(0)
                .doRead();
        for (int i = 0; i < 3; i++) {
            EasyExcel.read(chinaExcel, OperationData.class, new PageReadListener<OperationData>(data -> {
                data.forEach(v -> v.setItemCode1(null));
                list.addAll(data);
            }))
                    .sheet(i)
                    .doRead();
        }
        return list;
    }

    private List<HdrDictItem> getHdrDictItem() {
        String templateCode = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\代码对照.xlsx";
        List<HdrDictItem> list = new ArrayList<>();
        EasyExcel.read(templateCode, HdrDictItem.class, new PageReadListener<HdrDictItem>(list::addAll))
                .sheet(0)
                .doRead();
        System.out.println(list.size());
        return list;
    }
}
