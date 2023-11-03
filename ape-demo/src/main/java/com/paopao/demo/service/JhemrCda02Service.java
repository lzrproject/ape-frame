package com.paopao.demo.service;

import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.JhemrCda02;
import com.baomidou.mybatisplus.extension.service.IService;
import com.paopao.demo.domain.vo.JhemrCda02VO;

import java.util.List;

/**
* @author lizerong
* @description 针对表【jhemr_cda02(门（急）诊病历)】的数据库操作Service
* @createDate 2023-10-30 15:03:41
*/
public interface JhemrCda02Service extends IService<JhemrCda02VO> {

    public List<JhemrCda02VO> getListByCondition(ExportParams exportParams, Long page);
}
