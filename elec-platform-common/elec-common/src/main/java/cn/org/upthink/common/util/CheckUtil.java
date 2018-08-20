package cn.org.upthink.common.util;

import cn.org.upthink.common.dto.BaseResult;
import cn.org.upthink.common.exception.InvalidParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author mask
 * @date 2018-6-12
 */
public class CheckUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckUtil.class);
    public static void checkNotNull(Map<String,Object> checkMap){
        for (Map.Entry<String, Object> entry : checkMap.entrySet()) {

            Object param = entry.getValue();
            if(param instanceof String && StringUtils.isBlank(param.toString())){
                throw new InvalidParamException(entry.getKey() + "不能为空");
            }else if(param == null){
                throw new InvalidParamException(entry.getKey() + "不能为空");
            }
        }
    }
    /**
     *校验远程服务调用结果 ，成功则返回content  失败则抛出异常
     * @param result
     * @param <T>
     * @return
     */
    public static<T> T checkResult(BaseResult<T> result){
        if(result==null){
            throw new RuntimeException("result is null");
        }
        if(result.getSuccess()){
            return result.getContent();
        }
        logger.error("来自订单子服务"+result.getCode() + ":"+result.getMessage());
        logger.error(result.toString());
        throw new InvalidParamException(result.getMessage());
    }
}

