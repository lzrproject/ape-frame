
package com.paopao.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis 配置信息
 *
 * @author loser
 * @date 2023/06/20
 */
@ConfigurationProperties("spring.redis")
public class RedisProperties {

    private String host = "127.0.0.1";

    private int port = 6379;

    private int database = 0;

    private String password = "";

    private int timeOut = 3000;

    private int maxTotal = 10000;

    private int maxIdLe = 50;

    private long maxWaitMillis = 5000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdLe() {
        return maxIdLe;
    }

    public void setMaxIdLe(int maxIdLe) {
        this.maxIdLe = maxIdLe;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
}
