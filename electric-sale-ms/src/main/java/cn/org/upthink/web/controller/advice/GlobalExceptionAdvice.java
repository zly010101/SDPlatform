package cn.org.upthink.web.controller.advice;

import cn.org.upthink.common.dto.BaseResult;
import cn.org.upthink.model.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常类处理
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler({IllegalStateException.class,NullPointerException.class,IllegalArgumentException.class})
    @ResponseBody
    public BaseResult<?> handlerIllegalStateException(Exception e){
        return getErrorResult(e.getMessage(),ResponseCode.INVALID_PARAM);
    }

    @ExceptionHandler()
    @ResponseBody
    public BaseResult<?> handlerException(Exception e){
        return getErrorResult(e.getMessage(),ResponseCode.HANDLER_EXCEPTION);
    }
    private BaseResult<String> getErrorResult(String errorCode,String msg){
        BaseResult<String> errorRet = new BaseResult<>();
        errorRet.setCode(errorCode);
        errorRet.setContent(null);
        errorRet.setMessage(msg);
        errorRet.setSuccess(false);
        return errorRet;
    }
}
