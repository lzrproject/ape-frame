package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

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

    @Select("<#noparse>${</#noparse>sqlStr}")
    List< Map<String ,Object>> sqlSelect(@Param("sqlStr") String sqlStr);
    @Insert("<#noparse>${</#noparse>sqlStr}")
    List< Map<String ,Object>> sqlInsert(@Param("sqlStr") String sqlStr);
    @Delete("<#noparse>${</#noparse>sqlStr}")
    List< Map<String ,Object>> sqlDelete(@Param("sqlStr") String sqlStr);
    @Update("<#noparse>${</#noparse>sqlStr}")
    List< Map<String ,Object>> sqlUpdate(@Param("sqlStr") String sqlStr);
}
</#if>
