package com.paopao.mode.filterPattern;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description 过滤规则
 */
public class WordCountArticleFilter extends BaseArticleFilter {


    @Override
    public boolean doFilter(ArticleContext articleContext) {
        Article article = articleContext.getArticle();
        Long wordCount = article.getWordCount();
        if (wordCount > 100) {
            return true;
        }
        return false;
    }
}
