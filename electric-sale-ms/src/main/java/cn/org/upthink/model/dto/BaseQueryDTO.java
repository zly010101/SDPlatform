package cn.org.upthink.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter@Setter@ToString
public class BaseQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer pageSize = 10;
    private Integer pageNo = 1;
}
