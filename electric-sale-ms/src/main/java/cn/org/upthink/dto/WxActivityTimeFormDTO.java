package cn.org.upthink.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="WxActivityTime对象", description="")
public class WxActivityTimeFormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="测试id", name="actId", required=false)
    private String actId;

    @ApiModelProperty(value="测试id", name="actName", required=false)
    private String actName;

    /**手动增加getter,setter*/

}