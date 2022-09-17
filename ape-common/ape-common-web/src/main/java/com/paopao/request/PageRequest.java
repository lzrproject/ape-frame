package com.paopao.request;

import lombok.Setter;

/**
 * @Author 111
 * @Date 2022/9/17 11:11
 * @Description
 */
@Setter
public class PageRequest {
    private Long pageNo = 1L;

    private Long pageSize = 10L;

    public Long getPageNo(){
        if(pageNo == null || pageNo < 1){
            return 1L;
        }
        return pageNo;
    }

    public Long getPageSize(){
        if(pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE){
            return 10L;
        }
        return pageSize;
    }
}
