package com.paopao.mode.filterPattern;

/**
 * @Author paoPao
 * @Date 2023/2/10
 * @Description
 */
public abstract class BaseArticleFilter implements IArticleFilter {

    public abstract boolean doFilter(ArticleContext articleContext);

    /**
     * 过滤逻辑
     */
    public void filter(ArticleContext articleContext) {
        // 过滤
        doFilter(articleContext);
        // 操作
        // 发送通知
        sendMessage();
    }

    private void sendMessage() {

    }
}
