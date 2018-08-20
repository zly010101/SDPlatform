package ${dto.basePackage};

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel(value="${dto.entitySimpleName}对象", description="")
public class ${dto.entitySimpleName}FormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

<#list fieldList.sqlMapColumnList as column>
  <#if column.isRequired()>
    @ApiModelProperty(value="${column.remark}", name="${column.propertyName}", required=true)
    private ${column.fieldType} ${column.propertyName};

  </#if>
  <#if !column.isRequired()>
    @ApiModelProperty(value="${column.remark}", name="${column.propertyName}", required=false)
    private ${column.fieldType} ${column.propertyName};

  </#if>
</#list>
    /**手动增加getter,setter*/

}