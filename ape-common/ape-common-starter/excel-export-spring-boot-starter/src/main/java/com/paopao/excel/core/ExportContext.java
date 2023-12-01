package com.paopao.excel.core;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 导出上下文
 *
 * @Author paopao
 * @Date 2023/07/25
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


    public static void main(String[] args) {
        Date date = new Date(1701360000000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));
//        long time = 1700755200000L;
//        long time1 = 1700646000000L;
//        long res = time1 / 1000 / 60 / 60 / 24;
//        System.out.println(res);
    }
}
