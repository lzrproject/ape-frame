package com.paopao.mode.filterPattern;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description 过滤接口
 */
public interface IArticleFilter {

    boolean doFilter(ArticleContext articleContext);
}
