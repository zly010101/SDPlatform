package ${controller.basePackage};

import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.web.BaseController;
import cn.org.upthink.common.dto.BaseResult;
import ${controller.entityTypeName};
import ${dto.basePackage}.${controller.entitySimpleName}FormDTO;
import ${dto.basePackage}.${controller.entitySimpleName}QueryDTO;
import ${service.basePackage}.${controller.entitySimpleName}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//import cn.org.upthink.frame.modules.sys.utils.UserUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* Created by rover on 2018-06-08.
*/
@Api(value="${controller.lowerCaseEntitySimpleName}Api", description = "${controller.lowerCaseEntitySimpleName}的接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping(value = "/${controller.bizName}")
public class ${controller.entitySimpleName}Controller extends BaseController {

    @Autowired
    private ${controller.entitySimpleName}Service ${controller.lowerCaseEntitySimpleName}Service;

    @ApiOperation(value ="获取${controller.lowerCaseEntitySimpleName}详细信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/${controller.lowerCaseEntitySimpleName}/{id}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> find${controller.entitySimpleName}(@PathVariable("id") String id) {
        ${controller.entitySimpleName} ${controller.lowerCaseEntitySimpleName} = null;
        try {
            ${controller.lowerCaseEntitySimpleName} = ${controller.lowerCaseEntitySimpleName}Service.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(${controller.lowerCaseEntitySimpleName}!=null){
            return getBaseResultSuccess(${controller.lowerCaseEntitySimpleName}, "有效对象");
        }else{
            return getBaseResultFail(null, "无效的id，没有获取到对象");
        }
    }

    @ApiOperation(value = "删除${controller.entitySimpleName}信息", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/${controller.lowerCaseEntitySimpleName}/{id}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> delete${controller.entitySimpleName}(@PathVariable("id") String id) {
        ${controller.entitySimpleName} ${controller.lowerCaseEntitySimpleName} = null;
        try {
            ${controller.lowerCaseEntitySimpleName} = ${controller.lowerCaseEntitySimpleName}Service.get(id);
            if(${controller.lowerCaseEntitySimpleName}!=null){
                ${controller.lowerCaseEntitySimpleName}Service.delete(${controller.lowerCaseEntitySimpleName});
                return getBaseResultSuccess(true, "已成功删除${controller.entitySimpleName}对象");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "无效的id，没有删除${controller.entitySimpleName}对象");
    }

    @ApiOperation(value="新增${controller.entitySimpleName}", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/${controller.lowerCaseEntitySimpleName}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> add${controller.entitySimpleName}(@ApiParam @RequestBody ${controller.entitySimpleName}FormDTO ${controller.lowerCaseEntitySimpleName}FormDTO) {
        try {
            ${controller.entitySimpleName} ${controller.lowerCaseEntitySimpleName} = new ${controller.entitySimpleName}();
            <#list fieldList.sqlMapColumnList as column>
                <#if !column.isId()>
            ${controller.lowerCaseEntitySimpleName}.${column.setterMethodSimpleName}(${controller.lowerCaseEntitySimpleName}FormDTO.${column.getterMethodSimpleName}());
                </#if>
            </#list>
            ${controller.lowerCaseEntitySimpleName}Service.save(${controller.lowerCaseEntitySimpleName});
            return getBaseResultSuccess(true, "保存${controller.entitySimpleName}成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "更新${controller.entitySimpleName}", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/${controller.lowerCaseEntitySimpleName}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> update${controller.entitySimpleName}(
                     @RequestParam(value = "id", required = true, defaultValue = "") String id,
                     @ApiParam @RequestBody ${controller.entitySimpleName}FormDTO ${controller.lowerCaseEntitySimpleName}FormDTO) {
        try {
            if(${controller.lowerCaseEntitySimpleName}Service.get(id)==null){
                return getBaseResultFail(false, "操作失败，不存在的${controller.entitySimpleName}。请传入有效的${controller.entitySimpleName}的ID。");
            }
            ${controller.entitySimpleName} ${controller.lowerCaseEntitySimpleName} = new ${controller.entitySimpleName}();
            ${controller.lowerCaseEntitySimpleName}.setId(id);
            <#list fieldList.sqlMapColumnList as column>
                <#if !column.isId()>
            ${controller.lowerCaseEntitySimpleName}.${column.setterMethodSimpleName}(${controller.lowerCaseEntitySimpleName}FormDTO.${column.getterMethodSimpleName}());
                </#if>
            </#list>
            ${controller.lowerCaseEntitySimpleName}Service.save(${controller.lowerCaseEntitySimpleName});
            return getBaseResultSuccess(true, "保存${controller.entitySimpleName}成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(false, "保存失败");
    }

    @ApiOperation(value = "${controller.entitySimpleName}列表查询", notes="", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/${controller.lowerCaseEntitySimpleName}", produces = "application/json;charset=UTF-8")
    public BaseResult<?> list${controller.entitySimpleName}(HttpServletRequest request, HttpServletResponse response, @ApiParam ${controller.entitySimpleName}QueryDTO ${controller.lowerCaseEntitySimpleName}QueryDTO) {
        try {
            ${controller.entitySimpleName} ${controller.lowerCaseEntitySimpleName} = new ${controller.entitySimpleName}();
            <#list fieldList.sqlMapColumnList as column>
                <#if column.isQuery()>
            ${controller.lowerCaseEntitySimpleName}.${column.setterMethodSimpleName}(${controller.lowerCaseEntitySimpleName}QueryDTO.${column.getterMethodSimpleName}());
                </#if>
            </#list>
            Page<${controller.entitySimpleName}> page = ${controller.lowerCaseEntitySimpleName}Service.findPage(new Page<${controller.entitySimpleName}>(request, response), ${controller.lowerCaseEntitySimpleName});
            if(page.getList().isEmpty()){
                return getBaseResultSuccess(new ArrayList<${controller.entitySimpleName}>(), "没有查询到有效的数据。");
            }
            return getBaseResultSuccess(page, "查询数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseResultFail(null, "查询数据失败");
    }

}
