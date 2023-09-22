package com.paopao.redis.core;

import redis.clients.jedis.Pipeline;

/**
 * 管道执行者
 *
 * @author loser
 * @date 2023/06/20
 */
public interface PipelineExecutor<T> {

    /**
     * 管道执行器
     *
     * @param pipeline 管道
     * @return 返回值
     */
    T execute(Pipeline pipeline);

}
