package cn.org.upthink.common.constant;

/**
 *
 * @Author：Jaince
 * @Date：2018/06/05
 * @Description：响应code定义
 */
public class MembersResponseCode extends BaseResponseCode {

    //--------------------------会员服务组响应码-------------------------
    /**
     * 一般性(通用)错误响应
     */
    public static final String COMMON_ERROR_RESPONSE = "2600";
    /**
     * 参数无效(错误)
     */
    public static final String INVALID_PARAM = "2601";
    /**
     * 参数不合法
     */
    public static final String UNLAWFUL_PARAM = "2602";
    /**
     * 参数为空
     */
    public static final String EMPTY_PARAM = "2603";

    /**
     * 解析请求上来的JSON/XML内容错误
     */
    public static final String PARSING_PARAM_ERROR = "2604";
    /**
     * 批量请求数据，请求body数据为空
     */
    public static final String UNDISCOVERED_REQUEST_DATA = "2605";
    /**
     * 批量请求数据，请求处理结束，部分异常
     */
    public static final String PARTIAL_ANOMALY__RESPONSE = "2606";

    /**
     * 未设置支付密码
     */
    public static final String UNSET_PAY_PASSWORD = "2607";
    /**
     * 验证结果不正确
     */
    public static final String VERIFY_RESULT_NOT_CORRECT = "2608";
    /**
     * 请求资源未找到
     */
    public static final String REQUEST_RESOURCES_NOT_FOUND = "2609";
    /**
     * 必传的字段数据尚未完善，请完善数据后重试
     */
    public static final String REQUIRE_DATA_NOT_COMPLETED = "2610";

    /**
     * 后台端响应错误
     */
    public static final String BACKGROUND_ERROR_RESPONSE = "0";
}
