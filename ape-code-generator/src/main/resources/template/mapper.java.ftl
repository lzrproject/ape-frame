package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Mapper
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {


    @Delete("DELETE FROM ${table.name} where CORG_CODE = ${r'#{hospitalCode}'}")
    int deleteCdaByCode(String hospitalCode);
}
</#if>
