package com.paopao.mode.chainPattern.mid;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: paoPao
 * @Date: 2023/7/27
 * @Description:
 */
@Component
@Data
public abstract class AbstractCheckHandler {

    /**
     * 当前处理器持有下一个处理器的引用
     */
    protected AbstractCheckHandler nextHandler;

    /**
     * 处理器配置
     */
    protected ProductCheckHandlerConfig config;

    /**
     *处理器执行方法
     */
    public abstract String handle(String param);

    /**
     * 链路传递
     * @param param
     * @return
     */
    protected String next(String param){
        if (Objects.isNull(nextHandler)) {
            return "true";
        }
        return nextHandler.handle(param);
    }
}
