package cn.org.upthink.model.type;


import lombok.Getter;
import lombok.Setter;

@Getter
public enum LoopTypeEnum {
    HOME(0,"首页");


    private final int type;
    private final String typeMsg;

    LoopTypeEnum(int type, String typeMsg) {
        this.type = type;
        this.typeMsg = typeMsg;
    }
}