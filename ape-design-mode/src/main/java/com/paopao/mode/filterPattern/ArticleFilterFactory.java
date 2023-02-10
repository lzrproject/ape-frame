package com.paopao.mode.filterPattern;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description 工厂类
 */
public class ArticleFilterFactory {

    /**
     * 创建对应的规则方法
     */
    public static List<IArticleFilter> createFilter(List<ArticleFilterEnum> articleFilterEnums) {
        List<IArticleFilter> articleFilters = new ArrayList<>();
        if (CollectionUtils.isEmpty(articleFilterEnums)){
            return articleFilters;
        }
        articleFilters = articleFilterEnums.stream().map(x -> {
            return createFilter(x);
        }).collect(Collectors.toList());
        return articleFilters;
    }

    public static IArticleFilter createFilter(ArticleFilterEnum articleFilterEnum) {
        IArticleFilter articleFilter = null;
        switch (articleFilterEnum) {
            case WORD_COUNT:
                articleFilter = new WordCountArticleFilter();
                break;
            default:
                break;
        }
        return articleFilter;
    }
}
