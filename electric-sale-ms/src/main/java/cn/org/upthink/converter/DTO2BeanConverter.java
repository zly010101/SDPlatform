package cn.org.upthink.converter;

import cn.org.upthink.entity.LoopImg;
import org.springframework.beans.BeanUtils;

public  class DTO2BeanConverter {
    public static<T> LoopImg getLoopImg(T formDTO){
        LoopImg bean = new LoopImg();
        BeanUtils.copyProperties(formDTO,bean);
        return bean;
    }

}
