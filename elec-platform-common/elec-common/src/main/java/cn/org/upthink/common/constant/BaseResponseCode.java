package cn.org.upthink.common.constant;

/**
 * Code响应值
 * @author ma
 * @Date 2018-06-20
 */
public abstract class BaseResponseCode {

    /**
     * 状态：成功
     */
    public static final Boolean SUCCESS = true;
    /**
     * 状态：失败
     */
    public static final Boolean FAIL = false;

    //-------------------------全局响应码-------------------------
    /**
     * 请求成功 执行正常
     */
    public static final String REQUEST_SUCCESS = "200";
    /**
     * 系统繁忙，或者出现异常情况响应码
     */
    public static final String ERROR = "-1";
    /**
     * token有效 sign有效
     */
    public static final String EFFECTIVE_REQUEST = "1000";
    /**
     * 无效的token
     */
    public static final String INVALID_TOKEN = "1001";
    /**
     * 无效的sign
     */
    public static final String INVALID_SIGN = "1002";
    /**
     * 请求参数头header中，没有token和sign
     */
    public static final String NO_TOKEN_SIGN = "1003";
    /**
     * 请求参数头header中，没jwt格式的token
     */
    public static final String NO_JWT_TOKEN = "1004";
    /**
     * 不存在的token记录(token不存在)
     */
    public static final String NON_EXISTENT_TOKEN = "1005";

    /**
     * 接口处理过程发生异常
     */
    public static final String HANDLER_EXCEPTION = "9000";
    /**
     * 成功获取到正常的JSON对象
     */
    public static final String SUCCESS_JSON = "9001";
    /**
     * 获取的JSON对象为空
     */
    public static final String NULL_JSON = "9002";
    /**
     * 成功获取到正常的JSON数组对象
     */
    public static final String SUCCESS_JSON_ARRAY = "9003";
    /**
     * 获取的JSON数组对象为空
     */
    public static final String NULL_JSON_ARRAY = "9004";
    /**
     * 提交后台处理操作成功
     */
    public static final String HANDLER_SUCCESS = "9005";

    /****###### 21**用于基础平台服务组的code值扩展 ######**/

    /****###### 22**用于消息服务组的code值扩展 ######**/

    /****###### 23**用于财务服务组的code值扩 ######**/

    /****###### 24**用于产品服务服务组的code值扩展 ######**/

    /****###### 25**用于商品的code值扩展 ######**/

    /****###### 26**用于会员服务组的code值扩展 ######**/

    /****###### 27**用于订单交易的code值扩展 ######**/
    /****###### 28**用于结算交易的code值扩展 ######**/
    /****###### 29**用于评论服务的code值扩展 ######**/
    /****###### 30**用于物流服务的code值扩展 ######**/
    /****###### 31**用于库存服务的code值扩展 ######**/
    /****###### 32**用于购物车服务的code值扩展 ######**/





    /****###### 35**用于普通商品结算服务的code值扩展 ######**/
}
