package ${package.Service};

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.jhdl.mrdr.handle.JhemrCdaStrategy;
import com.jhdl.mrdr.entity.JhemrCdaEnum;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import com.baomidou.dynamic.datasource.annotation.DS;
import com.jhdl.mrdr.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
@DS("JHMRDR")
<#if kotlin>
open class ${table.serviceName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements JhemrCdaStrategy<${entity}> {

    @Override
    public JhemrCdaEnum getCdaName() {
        return JhemrCdaEnum.${table.name?upper_case};
    }

    @Override
    @Transactional
    public void saveDataToEntity(List<Map<String, String>> datas) throws ParseException {
        List<${entity}> entityDataList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> data = datas.get(i);
            DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ${entity} entityData = new ${entity}();
            <#list table.fields as field>
            <#if field.name == "AGE_VALUE">
            List<String> ageValue = SplitUtil.splitStr(data.get("AGE_VALUE"));
            entityData.setAgeValue(Integer.parseInt(ageValue.get(0)));
            entityData.setAgeUnit(ageValue.get(1));
            <#elseif field.propertyType == "Double">
            entityData.set${field.capitalName}(SplitUtil.isDouble(data.get("${field.name}")));
            <#elseif field.propertyType == "Date">
            entityData.set${field.capitalName}(SplitUtil.stringToDate(data.get("${field.name}")));
            <#elseif field.propertyType == "Integer">
            entityData.set${field.capitalName}(SplitUtil.isInteger(data.get("${field.name}")));
            <#else>
            entityData.set${field.capitalName}(data.get("${field.name}"));
            </#if>
            </#list>
            entityDataList.add(entityData);
        }
        try {
            this.saveBatch(entityDataList, 1000);
        } catch (Exception e) {
            log.error("${table.serviceName}.saveDataToEntity.error：{}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 写入文件
            SplitUtil.writeFile(JSONObject.toJSONString(datas), this.getCdaName().name());
        }
    }

}
</#if>
