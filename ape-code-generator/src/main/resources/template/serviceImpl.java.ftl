package ${package.Service};

import com.jhdl.mrdr.handle.JhemrCdaStrategy;
import ${package.Entity}.${entity};
import com.jhdl.mrdr.entity.JhemrCdaEnum;
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import com.baomidou.dynamic.datasource.annotation.DS;
import com.jhdl.mrdr.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public ${entity} saveDataToEntity(Map<String, String> datas) throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ${entity} entityData = new ${entity}();
        <#list table.fields as field>
        <#if field.name == "AGE_VALUE">
        List<String> ageValue = SplitUtil.splitStr(datas.get("AGE_VALUE"));
        entityData.setAgeValue(Integer.parseInt(ageValue.get(0)));
        entityData.setAgeUnit(ageValue.get(1));
        <#elseif field.propertyType == "Double">
        entityData.set${field.capitalName}(Double.valueOf(datas.get("${field.name}")));
        <#elseif field.propertyType == "Date">
        if ("".equals(datas.get("${field.name}"))) {
            entityData.set${field.capitalName}(null);
        } else {
            entityData.set${field.capitalName}(fmt.parse(datas.get("${field.name}")));
        }
        <#elseif field.propertyType == "Integer">
        entityData.set${field.capitalName}(Integer.valueOf(datas.get("${field.name}")));
        <#else>
        entityData.set${field.capitalName}(datas.get("${field.name}"));
        </#if>
        </#list>
        return entityData;
    }

}
</#if>
