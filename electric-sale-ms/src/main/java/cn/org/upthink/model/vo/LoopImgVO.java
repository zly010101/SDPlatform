package cn.org.upthink.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter@Setter@ToString
public class LoopImgVO extends BaseVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value="轮播图地址", name="loopUrl", required=true)
    private String loopUrl;

    @ApiModelProperty(value="轮播图类型(位置)", name="loopType", required=true)
    private String loopType;

    @ApiModelProperty(value="轮播图名称", name="loopName", required=true)
    private String loopName;
}
