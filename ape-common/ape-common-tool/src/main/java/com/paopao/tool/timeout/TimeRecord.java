package com.paopao.tool.timeout;

import java.lang.annotation.*;

/**
 * @Author: paoPao
 * @Date: 2023/2/21
 * @Description: 记录接口请求时间
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeRecord {


}
