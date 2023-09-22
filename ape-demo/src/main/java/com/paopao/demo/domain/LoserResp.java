package com.paopao.demo.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class LoserResp {

    @Excel(name = "用户ID", width = 18)
    public Long userId;

    @Excel(name = "用户名称", width = 18)
    public String userName;

}


