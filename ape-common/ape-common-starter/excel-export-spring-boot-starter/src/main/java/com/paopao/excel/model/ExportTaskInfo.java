package com.paopao.excel.model;

import com.paopao.excel.constant.ExportStatusEnum;
import lombok.Data;

/**
 * 任务信息
 *
 * @author loser
 * @Date 2023/07/25
 */
@Data
public class ExportTaskInfo {

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 操作用户
     */
    private String userId;

    /**
     * 状态
     *
     * @see ExportStatusEnum
     */
    private String status;

    /**
     * 文件分类
     */
    private String exportCategory;

    /**
     * 搜索条件
     */
    private Object searchCondition;

    /**
     * 任务创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 导出总数量
     */
    private Long total;

    /**
     * 当前导出数量
     */
    private Long curNum;

}