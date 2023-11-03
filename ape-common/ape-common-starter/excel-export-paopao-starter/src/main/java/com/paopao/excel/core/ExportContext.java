package com.paopao.excel.core;

import lombok.Data;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 导出上下文
 *
 * @Author paoPao
 * @Date 2023/11/1
 */
@Data
public class ExportContext {
    private Map<String, Object> contexts = new ConcurrentHashMap<>();

    public <T> T getContext(String key, Class<T> clazz) {
        Object res = contexts.get(key);
        if (Objects.isNull(res)) {
            return null;
        }
        return (T) res;
    }

    public void put(String key, Object value) {
        contexts.put(key, value);
    }

    public static ExportContext empty() {
        return new ExportContext();
    }
}
