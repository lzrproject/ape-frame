package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    PageInfo<${entity}> findPage(${entity} ${entity?uncap_first}, Integer pageNum, Integer pageSize);

    ${entity} selectById(Long id);

    Integer delById(Long id);

    Integer updateByIds(${entity} ${entity?uncap_first});

    List< Map<String ,Object>> execSql(String sql);

}
</#if>
