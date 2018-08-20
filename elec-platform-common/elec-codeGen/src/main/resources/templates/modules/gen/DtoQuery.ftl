package ${dto.basePackage};

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="${dto.entitySimpleName}对象", description="")
public class ${dto.entitySimpleName}QueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

<#list fieldList.sqlMapColumnList as column>
    <#if column.isQuery()>
    @ApiModelProperty(value="${column.remark}", name="${column.propertyName}", required=false)
    private ${column.fieldType} ${column.propertyName};

    </#if>
</#list>
    /**手动增加getter,setter*/

}