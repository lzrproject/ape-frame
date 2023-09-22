package com.paopao.redis.core;

import redis.clients.jedis.Jedis;

/**
 * redis 执行器
 *
 * @author loser
 * @date 2023/06/20
 */
public interface JedisExecutor<T> {

    /**
     * 执行并返回结果
     *
     * @param jedis redis 连接
     * @return 返回结果
     */
    T execute(Jedis jedis);

}
