package cn.org.upthink.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 说明：AES 加密工具类
 *
 * @author legend
 * @date 2018/2/2810:49
 */
public class AesUtil {

    public static final String VIPARA = "D1F07625B8E04A5D";
    public static final String bm = "UTF-8";

    public static String encrypt(String content, String dataPassword)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(content.getBytes(bm));

        return Base64.encode(encryptedData);
    }

    public static String decrypt(String content, String dataPassword)
            throws Exception {
        byte[] byteMi = Base64.decode(content);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData, bm);
    }


    public static String encrypt(String content, String dataPassword, String ivParamter)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(ivParamter.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
//        byte[] encryptedData = cipher.doFinal(content.getBytes());
        byte[] encryptedData = cipher.doFinal(content.getBytes(bm));

        return Base64.encode(encryptedData);
    }

    public static String decrypt(String content, String dataPassword, String ivParamter)
            throws Exception {
        byte[] byteMi = Base64.decode(content);
        IvParameterSpec zeroIv = new IvParameterSpec(ivParamter.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData, bm);
    }

}
