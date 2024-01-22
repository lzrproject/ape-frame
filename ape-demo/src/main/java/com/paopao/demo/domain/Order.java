package com.paopao.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2024/1/22
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    private String orderId;
    private String orderName;
    private Double price;
    private LocalDateTime createTime;
    private String desc;
    private Integer req;

}
