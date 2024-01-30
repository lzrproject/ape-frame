package com.paopao.cda.utils;

import cn.hutool.core.util.StrUtil;
import com.paopao.cda.domain.HdrDictItem;
import com.paopao.cda.domain.OperationData;
import com.paopao.cda.domain.SimilarParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.CosineSimilarity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/26
 */
@Slf4j
public class SimilarUtil<T> {

//    private List<T> itemList;
    private List<SimilarParam<T>> simList;

    private Class<T> clazz;

    private CosineSimilarity similarity;

    private boolean flag;

    private StringBuilder sb;

    public SimilarUtil(List<T> itemList, Class<T> clazz) {
        try {
            this.flag = clazz.newInstance() instanceof HdrDictItem;
        } catch (Exception e) {
            log.error("similarByCosine error", e);
        }
        this.similarBatch(itemList);
        this.clazz = clazz;
        this.similarity = new CosineSimilarity();
        this.sb = new StringBuilder();

    }

    private void similarBatch(List<T> itemList) {
        simList = new ArrayList<>(itemList.size());
        itemList.forEach(v -> {
            if (flag) {
                HdrDictItem data = (HdrDictItem) v;
                SimilarParam<T> param1 = new SimilarParam<>();
                // itemValue
                Map<CharSequence, Integer> map1 = Arrays.stream(data.getItemValue().split(""))
                        .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
                param1.setItem(v);
                param1.setSimilarVal(map1);
                simList.add(param1);
                // matchName
                Map<CharSequence, Integer> map2 = Arrays.stream(data.getMatchName().split(""))
                        .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
                SimilarParam<T> param2 = new SimilarParam<>();
                param2.setItem(v);
                param2.setSimilarVal(map2);
                simList.add(param2);
            }else {
                OperationData data = (OperationData) v;
                Map<CharSequence, Integer> map2 = Arrays.stream(data.getItemName().split(""))
                        .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
                SimilarParam<T> param = new SimilarParam<>();
                param.setItem(v);
                param.setSimilarVal(map2);
                simList.add(param);
            }
        });

    }

    public <T> String similarByCosine(String targetCode, String targetWord) {
        Map<CharSequence, Integer> map1 = Arrays.stream(targetWord.split(""))
                .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
        Map<String, Double> simHashMap = new ConcurrentHashMap<>();
        // 计算相似度
        simList.forEach(v -> {
            if (flag) {
                HdrDictItem data = (HdrDictItem) v.getItem();
                Double similar = similarity.cosineSimilarity(map1, v.getSimilarVal());
                if (simHashMap.containsKey(data.getItemCode() + "^" + data.getItemValue())) {
                    Double tmp = simHashMap.get(data.getItemCode() + "^" + data.getItemValue());
                    if (tmp < similar) {
                        simHashMap.put(data.getItemCode() + "^" + data.getItemValue(), similar);
                    }
                }else {
                    simHashMap.put(data.getItemCode() + "^" + data.getItemValue(), similar);
                }
            } else if (v.getItem() instanceof OperationData) {
                OperationData data = (OperationData) v.getItem();

                Double similar = similarity.cosineSimilarity(map1, v.getSimilarVal());
                simHashMap.put((StrUtil.isEmpty(data.getItemCode1()) ? data.getItemCode2() : data.getItemCode1())
                        + "^" + data.getItemName(), similar);
            }
        });
//        if (flag) {
//            simList.forEach(v -> {
//                HdrDictItem data = (HdrDictItem) v.getItem();
//                Double similar = similarity.cosineSimilarity(map1, v.getSimilarVal());
////                simHashMap.put(data.getItemCode() + "^" + data.getItemValue(), similar);
//                if (simHashMap.containsKey(data.getItemCode() + "^" + data.getItemValue())) {
//                    Double tmp = simHashMap.get(data.getItemCode() + "^" + data.getItemValue());
//                    if (tmp < similar) {
//                        simHashMap.put(data.getItemCode() + "^" + data.getItemValue(), similar);
//                    }
//                }else {
//                    simHashMap.put(data.getItemCode() + "^" + data.getItemValue(), similar);
//                }
////                simHashMap.compute(data.getItemCode() + "^" + data.getItemValue(), (str, doub) -> {
////                    if (doub != null && doub < similar) {
////                        return similar;
////                    }
////                    return doub;
////                });
//            });
//        }
        return this.sortMap(simHashMap, targetCode, targetWord);
    }


    private String sortMap(Map<String, Double> map, String targetCode, String targetWord) {
        Optional<Map.Entry<String, Double>> maxEntry = map.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue));
        String key = maxEntry.get().getKey();
        Double value = maxEntry.get().getValue();
        if (value < 0.6) {
            sb.append(key);
//            if (StrUtil.isNotEmpty(targetCode)) {
//                sb.append(targetCode);
//            }
//            sb.append("^")
//                    .append(targetWord);

            sb.append("^");
            if (StrUtil.isNotEmpty(targetCode)) {
                sb.append(targetCode);
            }
            sb.append("^")
                    .append(targetWord)
                    .append("\n");
            return null;
        }
        return key;
    }

    public String getLowVal() {
        return sb.toString();
    }

    public static void main(String[] args) {
        String fileName = "E:\\idea\\gitInfo\\compInfo\\company\\业务资料\\CDA交互服务\\op.txt";
        List<String> strList = FileUtilCommon.readFile(fileName);
        strList.forEach(v -> {
            String[] split = v.split("\\^");
            if (split.length != 4) {
                System.out.println("1111" + v);
            }
        });
        System.out.println(strList);
    }
}
