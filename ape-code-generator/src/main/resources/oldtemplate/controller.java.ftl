package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#--import org.apache.shiro.authz.annotation.Logical;-->
<#--import org.apache.shiro.authz.annotation.RequiresPermissions;-->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ${package.Entity}.Result;
import ${package.Entity}.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;

<#--import com.common.res.DataResult;-->
<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(tags = "${table.controllerName} ${table.comment!}")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@CrossOrigin
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

<#--    @Autowired-->
    private ${table.serviceName} ${table.serviceName?uncap_first};

    public ${table.controllerName}(${table.serviceName} ${table.serviceName?uncap_first}){
        this.${table.serviceName?uncap_first} = ${table.serviceName?uncap_first};
    }

    @ApiOperation(value = "分页列表", response = Result.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${table.entityName?uncap_first}", value = "实体", dataType = "${table.entityName}"),
        @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer"),
        @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "Integer")
    })
    @PostMapping(value = "/findPage/{pageNum}/{pageSize}")
    public Result<PageInfo> findPage(@RequestBody(required = false) ${table.entityName} ${table.entityName?uncap_first},
                            @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {

        PageInfo<${table.entityName}> pageInfo = ${table.serviceName?uncap_first}.findPage(${table.entityName?uncap_first}, pageNum, pageSize);
        return new Result(true, StatusCode.SUCCESS,"查询成功",pageInfo);
    }

    @ApiOperation(value = "根据ID查询单个", response = Result.class)
    @ApiImplicitParams({
<#--        @ApiImplicitParam(name = "${table.entityName?uncap_first}", value = "实体", dataType = "${table.entityName}"),-->
<#--        @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer"),-->
        @ApiImplicitParam(name = "${table.entityName?uncap_first}Id", value = "主键编号", dataType = "Long")
    })
    @GetMapping("/selectById")
    public Result selectById(@RequestParam(required = true) Long ${table.entityName?uncap_first}Id){
        ${entity} selectById = ${table.serviceName?uncap_first}.selectById(${table.entityName?uncap_first}Id);
        return new Result(true, StatusCode.SUCCESS,"查询成功",selectById);
    }

    @ApiOperation(value = "根据ID删除数据", response = Result.class)
    @ApiImplicitParams({
    <#--        @ApiImplicitParam(name = "${table.entityName?uncap_first}", value = "实体", dataType = "${table.entityName}"),-->
    <#--        @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer"),-->
        @ApiImplicitParam(name = "${table.entityName?uncap_first}Id", value = "主键编号", dataType = "Long")
    })
    @DeleteMapping("/delById")
    public Result delById(@RequestParam(required = true) Long ${table.entityName?uncap_first}Id){
        Integer delById = ${table.serviceName?uncap_first}.delById(${table.entityName?uncap_first}Id);
        return new Result(true, StatusCode.SUCCESS,"删除成功",delById);
    }

    @ApiOperation(value = "根据ID修改数据", response = Result.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${table.entityName?uncap_first}", value = "实体", dataType = "${table.entityName}"),
    <#--        @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer"),-->
<#--        @ApiImplicitParam(name = "${table.entityName?uncap_first}Id", value = "主键编号", dataType = "Long")-->
    })
    @PutMapping("/updateById")
    public Result updateById(@RequestBody(required = false) ${table.entityName} ${table.entityName?uncap_first}){
        Integer updateById = ${table.serviceName?uncap_first}.updateByIds(${table.entityName?uncap_first});
        return new Result(true, StatusCode.SUCCESS,"修改成功",updateById);
    }

    @ApiOperation(value = "自定义sql查询", response = Result.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sql", value = "sql语句", dataType = "String"),
    })
    @GetMapping("/execSql")
    public Result execSql(@RequestParam String sql) {
        List< Map<String ,Object>> execSql = ${table.serviceName?uncap_first}.execSql(sql);
        if ("error".equals(execSql.get(0).get(0))) return new Result(false, StatusCode.ERROR_RESPONSE,"请输入正确sql");
        return new Result(true, StatusCode.SUCCESS,"执行成功",execSql);
    }

}
</#if>