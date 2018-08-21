package cn.org.upthink.conver;

import cn.org.upthink.entity.LoopImg;
import cn.org.upthink.model.dto.LoopImgFormDTO;
import cn.org.upthink.model.dto.LoopImgQueryDTO;
import org.springframework.beans.BeanUtils;

public  class DTO2BeanConver {
    public static<T> LoopImg getLoopImg(T formDTO){
        LoopImg bean = new LoopImg();
        BeanUtils.copyProperties(formDTO,bean);
        return bean;
    }

}
