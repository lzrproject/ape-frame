package com.paopao.mode.filterPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description 测试类(有问题) articleFilterEnums如何创建list
 */
public class ArticleFilterDemo {
    private List<ArticleFilterEnum> articleFilterEnums;

    private List<IArticleFilter> articleFilters;

    public ArticleFilterDemo(List<ArticleFilterEnum> articleFilterEnums) {
        this.articleFilterEnums = articleFilterEnums;
        this.init();
    }

    private void init() {
        this.articleFilters = ArticleFilterFactory.createFilter(articleFilterEnums);
    }

    public void doFilter(List<Article> articles) {
        articles.stream().filter(article -> {
            ArticleContext articleContext = new ArticleContext();
            articleContext.setArticle(article);
            return doFilter(articleContext);
        }).collect(Collectors.toList());
    }

    public boolean doFilter(ArticleContext articleContext) {
        for (IArticleFilter articleFilter : articleFilters) {
            if (articleFilter.doFilter(articleContext)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        new
    }
}
