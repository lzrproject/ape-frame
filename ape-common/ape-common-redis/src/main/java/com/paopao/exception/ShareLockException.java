package com.paopao.exception;

/**
 * @Author paoPao
 * @Date 2022/10/4 15:10
 * @Description Redis分布式异常
 */
public class ShareLockException extends RuntimeException {

    public ShareLockException(String message){
        super(message);
    }
}
