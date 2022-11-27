package com.paopao.mode.templatePattern.prod;

/**
 * @Author: paoPao
 * @Date: 2022/11/27
 * @Description:
 */
public interface Action {
    /**
     * 参数校验，可以自定义异常抛出
     */
    void validate();

    /**
     * 执行
     */
    void execute();

    /**
     * 后续
     */
    void after();

}
