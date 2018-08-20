package cn.org.upthink.common.exception;



/**
 * @author mask
 * @date 2018-5-30
 */
public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String arg0) {
        super(arg0);
    }

    public InvalidParamException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
