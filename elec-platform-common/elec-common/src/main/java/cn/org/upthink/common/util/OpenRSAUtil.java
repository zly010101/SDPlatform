package cn.org.upthink.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rover on 2018/3/14.
 */
public class OpenRSAUtil {

    protected static Logger logger = LoggerFactory.getLogger(OpenRSAUtil.class);

    /**
     * http请求密钥算法因子连接符
     */
    public static final String REQ_KEY_STR = ".http.";

    /**
     * http请求密钥信息参数因子连接符
     */
    public static final String DECRYPT_KEY_STR = ",";

    public static final String SIGN_JOIN_STR = ",";


    public static Map<String, String> getSourceInfo(String serialNo, String encodeKeyStr, String encodeContentStr) {
        if (StringUtils.isEmpty(serialNo) || StringUtils.isEmpty(encodeKeyStr) || StringUtils.isEmpty(encodeContentStr)) {
            return Collections.emptyMap();
        }
        try {
            logger.info("获取sourceInfo时，根密钥serialNo=" + serialNo);
            logger.info("获取sourceInfo时，密文encodeKeyStr=" + encodeKeyStr);
            String decodeKeyStr = AesUtil.decrypt(encodeKeyStr, serialNo);
            logger.info("获取sourceInfo时，解出明文：decodeKeyStr=" + decodeKeyStr);
            if (StringUtils.isEmpty(decodeKeyStr)) {
                return Collections.emptyMap();
            }
            String[] strs = decodeKeyStr.split(DECRYPT_KEY_STR);
            if (strs == null || strs.length != 3) {
                return Collections.emptyMap();
            }
            //计算请求密钥
            String reqKey = null;
            try {
                reqKey = MD5.getMD5(AesUtil.encrypt(strs[0] + REQ_KEY_STR + strs[1] + REQ_KEY_STR + strs[2], strs[2]));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return Collections.emptyMap();
            }
            if (StringUtils.isEmpty(reqKey)) {
                return Collections.emptyMap();
            }
            //解密密文
            String realContent = AesUtil.decrypt(encodeContentStr, reqKey);
            if (StringUtils.isEmpty(realContent)) {
                return Collections.emptyMap();
            }
            Map<String, String> realSourceMap = new HashMap<>(4);
            strs = realContent.split(REQ_KEY_STR);
            if (strs == null || (strs != null && strs.length != 4)) {
                return Collections.emptyMap();
            }
            realSourceMap.put("snCode", strs[0]);
            realSourceMap.put("deviceCode", strs[1]);
            realSourceMap.put("timeMillis", strs[2]);
            realSourceMap.put("randomStr", strs[3]);
            realSourceMap.put("reqKey", reqKey);
            return realSourceMap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyMap();
    }

    public static String getSign(String content, String privateKey) {
        return RSASignature.sign(content, privateKey);
    }

}
