package com.paopao.excel.constant;

import lombok.Getter;

/**
 * 导出状态枚举
 *
 * @Author paoPao
 * @Date 2023/11/2
 */
@Getter
public enum ExportStatusEnum {

    INIT(0, "INIT", "初始化"),
    LOADING(1, "LOADING", "导出中"),
    FINISH(2, "FINISH", "已完成"),
    FAILURE(3, "FAILURE", "失败"),
    DELETED(4, "DELETED", "已删除"),
    USER_STOP(5, "STOP", "用户终止任务");

    private final Integer id;
    private final String status;
    private final String desc;

    ExportStatusEnum(Integer id, String status, String desc) {
        this.id = id;
        this.status = status;
        this.desc = desc;
    }

}
