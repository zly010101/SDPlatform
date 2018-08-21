package cn.org.upthink.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter@Setter@ToString
public class BaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Date updateDate;
    private Date createDate;
}
