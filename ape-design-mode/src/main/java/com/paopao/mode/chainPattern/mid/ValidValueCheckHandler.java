package com.paopao.mode.chainPattern.mid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: paoPao
 * @Date: 2023/7/27
 * @Description: 有效值校验
 */
@Slf4j
@Component
public class ValidValueCheckHandler extends AbstractCheckHandler {

    @Override
    public String handle(String param) {
        log.info("Valid Value Check Success" + param);
        return super.next("1");
    }
}
