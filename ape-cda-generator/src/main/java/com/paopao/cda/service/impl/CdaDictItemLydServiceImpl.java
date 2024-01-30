package com.paopao.cda.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paopao.cda.domain.CdaDictItemLyd;
import com.paopao.cda.service.CdaDictItemLydService;
import com.paopao.cda.mapper.CdaDictItemLydMapper;
import org.springframework.stereotype.Service;

/**
* @author lizerong
* @description 针对表【CDA_Dict_Item_LYD】的数据库操作Service实现
* @createDate 2024-01-24 15:18:47
*/
@Service
@DS("CDAVIEW")
public class CdaDictItemLydServiceImpl extends ServiceImpl<CdaDictItemLydMapper, CdaDictItemLyd>
    implements CdaDictItemLydService{

}




