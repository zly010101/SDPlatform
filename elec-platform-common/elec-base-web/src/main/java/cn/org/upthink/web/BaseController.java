package cn.org.upthink.web;


import cn.org.upthink.common.dto.BaseResult;
import cn.org.upthink.common.util.DateUtils;
import cn.org.upthink.common.util.JsonMapper;
import cn.org.upthink.common.util.StringUtils;
import cn.org.upthink.persistence.mybatis.dto.Page;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.function.Supplier;

/**
 * 抽象的Controller类
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 客户端返回JSON字符串
     * @param response
     * @param object
     * @return
     */
    protected void renderString(HttpServletResponse response, Object object) {
        renderString(response, JsonMapper.toJsonString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     * @param response
     * @param string
     * @return
     */
    protected void renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {
        return "error/400";
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /**String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击*/
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                /**转码*/
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        /**Date 类型转换*/
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /****===================================BaseResult=====================================**/

    /**
     * 返回结果对象
     * @param content
     * @param code
     * @param success
     * @param message
     * @return
     */
    public BaseResult<Object> getBaseResult(Object content, String code, boolean success, String message){
        BaseResult<Object> baseResult = new BaseResult<Object>();
        baseResult.setContent(content);
        baseResult.setCode(code);
        baseResult.setSuccess(success);
        baseResult.setMessage(message);
        return baseResult;
    }

    /**
     * 返回成功状态的结果对象
     * @param content
     * @param message
     * @return
     */
    public BaseResult<Object> getBaseResultSuccess(Object content, String message){
        return getBaseResult(content, "200", true, message);
    }

    /**
     * 返回成功状态的结果对象
     * @param content
     * @param code
     * @param message
     * @return
     */
    public BaseResult<Object> getBaseResultSuccess(Object content, String code, String message){
        return getBaseResult(content, code, true, message);
    }

    /**
     * 返回失败状态的结果对象
     * @param content
     * @param message
     * @return
     */
    public BaseResult<Object> getBaseResultFail(Object content, String message){
        return getBaseResult(content, "200", false, message);
    }

    /**
     * 返回失败状态的结果对象
     * @param content
     * @param code
     * @param message
     * @return
     */
    public BaseResult<Object> getBaseResultFail(Object content, String code, String message){
        return getBaseResult(content, code, false, message);
    }

    /**
     * 获取分页信息
     * @param request
     * @return
     */
    public Page getPage(HttpServletRequest request){
        if(request == null){
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = requestAttributes.getRequest();
        }
        Page page = new Page();
        String pageno = request.getParameter("pageNo");
        if(pageno!=null && !"".equals(pageno) && StringUtils.isInteger(pageno)){
            page.setPageNo(Integer.parseInt(pageno));
        }
        String pagesize = request.getParameter("pageSize");
        if(pagesize!=null && !"".equals(pagesize) && StringUtils.isInteger(pagesize)){
            page.setPageSize(Integer.parseInt(pagesize));
        }
        return page;
    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }




}
