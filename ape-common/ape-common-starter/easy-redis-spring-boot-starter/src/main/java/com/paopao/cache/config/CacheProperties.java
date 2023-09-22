package com.paopao.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存配置 配置信息
 *
 * @author loser
 * @date 2023/06/20
 */
@ConfigurationProperties("loser.cache")
public class CacheProperties {

    private String pre = "BIZ:CACHE:";

    private String pack = "";

    public String getPre() {
        return pre;
    }

    public String getPack() {
        return pack;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }
}
