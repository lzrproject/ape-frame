package com.paopao.demo.mapper;

import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.JhemrCda02;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paopao.demo.domain.vo.JhemrCda02VO;

import java.util.List;

/**
* @author lizerong
* @description 针对表【jhemr_cda02(门（急）诊病历)】的数据库操作Mapper
* @createDate 2023-10-30 15:03:41
* @Entity com.paopao.demo.domain.JhemrCda02
*/
public interface JhemrCda02Mapper extends BaseMapper<JhemrCda02VO> {

    public List<JhemrCda02VO> selectListByCondition(ExportParams exportParams);
}




