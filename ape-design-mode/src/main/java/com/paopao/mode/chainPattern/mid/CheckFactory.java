package com.paopao.mode.chainPattern.mid;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: paoPao
 * @Date: 2023/7/27
 * @Description:
 */
@Component
public class CheckFactory {

    @Resource
    private Map<String, AbstractCheckHandler> handlerMap;

    public AbstractCheckHandler getHandler(ProductCheckHandlerConfig config) {
        return this.checkHandler(config);
    }

    /**
     * 封装处理器配置
     *
     * @param config
     * @return
     */
    private AbstractCheckHandler checkHandler(ProductCheckHandlerConfig config) {
        // 配置检查
        if (Objects.isNull(config)) {
            return null;
        }
        // 配置错误
        String handler = config.getHandler();
        if (StringUtils.isBlank(handler)) {
            return null;
        }
        // 配置了不存在的处理器
        AbstractCheckHandler abstractCheckHandler = handlerMap.get(handler);
        if (Objects.isNull(abstractCheckHandler)) {
            return null;
        }
        abstractCheckHandler.setConfig(config);
        // 递归处理
        abstractCheckHandler.setNextHandler(this.checkHandler(config.getNext()));
        return abstractCheckHandler;
    }
}
