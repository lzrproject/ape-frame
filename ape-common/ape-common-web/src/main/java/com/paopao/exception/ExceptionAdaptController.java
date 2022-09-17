package com.paopao.exception;

import com.paopao.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 111
 * @Date 2022/9/6 14:44
 * @Description 全局异常处理类
 */
@RestControllerAdvice
public class ExceptionAdaptController {

    @ExceptionHandler({RuntimeException.class})
    public Result runTimeException(RuntimeException e) {
        e.printStackTrace();
        return Result.fail("运行时异常："+e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.fail("异常："+e.getMessage());
    }
}
