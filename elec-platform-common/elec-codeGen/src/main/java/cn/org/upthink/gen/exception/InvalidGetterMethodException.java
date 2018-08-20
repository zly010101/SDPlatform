package cn.org.upthink.gen.exception;

/**
 * 非法的getter方法
 * <功能详细描述>
 */
public class InvalidGetterMethodException extends SILException{

   /** 注释内容 */
   private static final long serialVersionUID = 7454235870627099536L;

   /**
    * @return
    */
   @Override
   protected String doGetErrorCode() {
       return "INVALID_GETTER_METHOD";
   }

   /**
    * @return
    */
   @Override
   protected String doGetErrorMessage() {
       return "非法的getter方法";
   }

   /** <默认构造函数> */
   public InvalidGetterMethodException(String message, Object[] parameters) {
       super(message, parameters);
   }

   /** <默认构造函数> */
   public InvalidGetterMethodException(String message) {
       super(message);
   }
}
