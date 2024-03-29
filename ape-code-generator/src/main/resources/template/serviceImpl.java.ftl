package ${package.Service};

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jhdl.mrdr.handle.JhemrCdaStrategy;
import com.jhdl.mrdr.entity.JhemrCdaEnum;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import com.baomidou.dynamic.datasource.annotation.DS;
import com.jhdl.mrdr.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Service
@Slf4j
@DS("CDAETL")
<#if kotlin>
open class ${table.serviceName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements JhemrCdaStrategy<${entity}> {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public JhemrCdaEnum getCdaName() {
        return JhemrCdaEnum.${table.name?upper_case};
    }

    @Override
    public String saveDataToEntity(List<Map<String, String>> datas, String jobType, String hospitalCode) {
        List<${entity}> entityDataList = new ArrayList<>();
        for (Map<String, String> data : datas) {
            try {
                ${entity} entityData = new ${entity}();
                <#list table.fields as field>
                <#if field.name == "AGE_VALUE">
                List<String> ageValue = SplitUtil.splitStr(data.get("AGE_VALUE"));
                entityData.setAgeValue(SplitUtil.isInteger(ageValue.get(0)));
                entityData.setAgeUnit(ageValue.get(1));
                <#elseif field.propertyType == "Double">
                entityData.set${field.capitalName}(SplitUtil.isDouble(data.get("${field.name}")));
                <#elseif field.propertyType == "Date">
                entityData.set${field.capitalName}(SplitUtil.stringToDate(data.get("${field.name}")));
                <#elseif field.propertyType == "Integer">
                entityData.set${field.capitalName}(SplitUtil.isInteger(data.get("${field.name}")));
                <#else>
                entityData.set${field.capitalName}(StrUtil.trim(data.get("${field.name}")));
                </#if>
                </#list>
                entityDataList.add(entityData);
            } catch (Exception e) {
                log.error("${table.serviceName}.saveDataToEntity.ID:{},error:{}", data.get("PK"), e.getMessage(), e);
            }
        }
        return transactionTemplate.execute(transactionStatus -> {
            String fileName = "";
            try {
                // 1-历史数据 2-增量数据
                if ("1".equals(jobType)) {
                    this.saveBatch(entityDataList, 1000);
                }else {
                    this.saveOrUpdateBatch(entityDataList, 500);
                }
            } catch (Exception e) {
                log.error("${table.serviceName}.saveDataToEntity.error：{}", e.getMessage(), e);
                transactionStatus.setRollbackOnly();
                // 写入文件
                fileName = SplitUtil.writeFile(JSON.toJSONString(datas), hospitalCode + "_" + this.getCdaName().name());
            }
            return fileName;
        });
    }

    @Override
    public int delByHospitalCode(String hospitalCode) {
        return this.baseMapper.deleteCdaByCode(hospitalCode);
    }

    @Override
    public String getLastDateTime(String hospitalCode) {
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        wrapper.select("max(`UPDATED_AT`) as updatedAt")
            .eq("CORG_CODE", hospitalCode);
        ${entity} entityDto = this.baseMapper.selectOne(wrapper);
        String lastDateTime = null;
        if (entityDto != null) {
            lastDateTime = entityDto.getUpdatedAt();
        }
        return lastDateTime;
    }
}
</#if>
