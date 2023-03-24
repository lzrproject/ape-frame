package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

<#--    @Autowired-->
    private ${table.mapperName} ${table.mapperName?uncap_first};

    public ${table.serviceImplName}(${table.mapperName} ${table.mapperName?uncap_first}){
        this.${table.mapperName?uncap_first} = ${table.mapperName?uncap_first};
    }

    /**
     * ${entity}条件+分页查询
     * @param ${entity?uncap_first} 查询条件
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页结果
    */
    @Override
    public PageInfo<${entity}> findPage(${entity} ${entity?uncap_first}, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Map<String ,Object> map = selectByMap(${entity?uncap_first});
        List<${entity}> keyList = ${table.mapperName?uncap_first}.selectByMap(map);
        return new PageInfo<>(keyList);
    }

    /**
     * 根据主键ID查询单个
     * @param id
     * @return ${entity}
     */
    @Override
    public ${entity} selectById(Long id) {
        return ${table.mapperName?uncap_first}.selectById(id);
    }

    /**
     * 根据ID删除数据
     * @param id
     * @return Integer
     */
    @Override
    public Integer delById(Long id) {
        return ${table.mapperName?uncap_first}.deleteById(id);
    }

    /**
     * 根据ID修改数据
     * @param ${entity?uncap_first}
     * @return Integer
     */
    @Override
    public Integer updateByIds(${entity} ${entity?uncap_first}) {
        return ${table.mapperName?uncap_first}.updateById(${entity?uncap_first});
    }

    /**
     * 自定义sql语句
     * @param sql
     * @return Object
     */
    @Override
    public List< Map<String ,Object>> execSql(String sql) {
        String lowerCase = sql.trim().toLowerCase();
        List< Map<String ,Object>> result = new ArrayList<>();;
        if (lowerCase.startsWith("select")) {
            result = ${table.mapperName?uncap_first}.sqlSelect(sql);
        }else if (lowerCase.startsWith("insert")) {
            result = ${table.mapperName?uncap_first}.sqlInsert(sql);
        }else if (lowerCase.startsWith("update")) {
            result = ${table.mapperName?uncap_first}.sqlUpdate(sql);
        }else if (lowerCase.startsWith("delete")) {
            result = ${table.mapperName?uncap_first}.sqlDelete(sql);
        }else {
            result.add(0,null);
        }
        return result;
    }

    public Map<String ,Object> selectByMap (${entity} ${entity?uncap_first}){
        Map<String ,Object> map = new HashMap<>();
        if (${entity?uncap_first} != null){
        <#list table.fields as field>
            // ${field.comment}
            if(!StringUtils.isEmpty(${entity?uncap_first}.get${field.capitalName}())){
                map.put("${field.name}" ,${entity?uncap_first}.get${field.capitalName}());
            }
        </#list>
        }
        return map;
    }

}


</#if>
