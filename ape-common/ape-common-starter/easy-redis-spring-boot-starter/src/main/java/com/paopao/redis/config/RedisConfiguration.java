package com.paopao.redis.config;

import com.paopao.redis.core.RedisHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 配置类
 *
 * @author loser
 * @date 2023/06/20
 */
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    private final RedisProperties properties;

    public RedisConfiguration(RedisProperties redisProperties) {
        this.properties = redisProperties;
    }

    @Bean(name = "redisHandler")
    public RedisHandler redisHandler() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(properties.getMaxTotal());
        //最多空闲数
        poolConfig.setMaxIdle(properties.getMaxIdLe());
        //当池中没有连接时，最多等待5秒
        poolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
        String password = properties.getPassword();
        String pw = (password == null || password.length() == 0) ? null : properties.getPassword();
        return new RedisHandler(poolConfig, properties.getHost(), properties.getPort(), properties.getTimeOut(), pw, properties.getDatabase());
    }

}
