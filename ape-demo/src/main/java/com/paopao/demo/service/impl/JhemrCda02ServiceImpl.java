package com.paopao.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.paopao.demo.domain.ExportParams;
import com.paopao.demo.domain.JhemrCda02;
import com.paopao.demo.domain.vo.JhemrCda02VO;
import com.paopao.demo.service.JhemrCda02Service;
import com.paopao.demo.mapper.JhemrCda02Mapper;
import com.paopao.request.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lizerong
 * @description 针对表【jhemr_cda02(门（急）诊病历)】的数据库操作Service实现
 * @createDate 2023-10-30 15:03:41
 */
@Service
public class JhemrCda02ServiceImpl extends ServiceImpl<JhemrCda02Mapper, JhemrCda02VO>
        implements JhemrCda02Service {

    /**
     * 每次分页数量
     */
    private static final Integer SIZE = 3000;

    @Override
    @DS("TEST")
    public List<JhemrCda02VO> getListByCondition(ExportParams exportParams, Long page) {
        exportParams.getLimit(page, SIZE);
        return this.baseMapper.selectListByCondition(exportParams);
    }

    @Override
    @DS("JHEMR")
    @Transactional(rollbackFor = Exception.class)
    public Integer batchInsertCda02(List<JhemrCda02VO> jhemrCda02VOS) {
        this.saveBatch(jhemrCda02VOS);
        return jhemrCda02VOS.size();
    }


}




