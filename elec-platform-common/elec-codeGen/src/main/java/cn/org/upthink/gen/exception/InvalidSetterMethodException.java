package cn.org.upthink.gen.exception;

/**
 * 非法的Set方法
 * <功能详细描述>
 */
public class InvalidSetterMethodException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -4601581578924296318L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "INVALID_SETTER_METHOD";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "非法的setter方法";
    }
    
    /** <默认构造函数> */
    public InvalidSetterMethodException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public InvalidSetterMethodException(String message) {
        super(message);
    }
}
