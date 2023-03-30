package com.paopao.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Author paoPao
 * @Date 2023/3/30
 * @Description:
 */
@Data
public class PageResult<T> implements Serializable {

    private Long total;

    private Long size;

    private Long current;

    private Long pages;

    private List<T> records = Collections.emptyList();

    public void loadData(IPage<T> pageData){
        this.setCurrent(pageData.getCurrent());
        this.setPages(pageData.getPages());
        this.setSize(pageData.getSize());
        this.setTotal(pageData.getTotal());
        this.setRecords(pageData.getRecords());
    }
}
