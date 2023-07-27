package com.paopao.mode.chainPattern.easy;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: paoPao
 * @Date: 2023/7/3
 * @Description: 请假信息
 */
@AllArgsConstructor
@Data
public class LeaveRequest {
    //请假人姓名
    private String name;
    //请假天数
    private Integer days;
}
