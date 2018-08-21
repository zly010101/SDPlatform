package cn.org.upthink.model.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel(value="LoopImg对象", description="")
public class LoopImgQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="轮播图地址", name="loopUrl", required=false)
    private String loopUrl;

    @ApiModelProperty(value="轮播图类型(位置)", name="loopType", required=false)
    private Integer loopType;

    @ApiModelProperty(value="轮播图名称", name="loopName", required=false)
    private String loopName;

    /**手动增加getter,setter*/

}