package com.paopao.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 111
 * @Date 2022/9/6 11:52
 * @Description
 */
@Data
@Builder
public class Result<T> implements Serializable {

    private boolean flag;
    private Integer code;
    private String message;
    private T data;


    public static Result success() {
        return Result.builder()
                .flag(true)
                .code(ResultCode.SUCCESS)
                .message(ResultMessage.SUCCESS)
                .build();
    }

    public static<T> Result success(T data) {
        return Result.builder()
                .flag(true)
                .code(ResultCode.SUCCESS)
                .message(ResultMessage.SUCCESS)
                .data(data)
                .build();
    }

    public static Result success(boolean flag, Integer code, String message) {
        return Result.builder()
                .flag(flag)
                .code(code)
                .message(message)
                .build();
    }

    public static<T> Result<T> success(boolean flag, Integer code, String message, T data) {
        return Result.<T>builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static Result fail(String message) {
        return Result.builder()
                .flag(true)
                .code(ResultCode.ERROR)
                .message(message)
                .build();
    }

}
