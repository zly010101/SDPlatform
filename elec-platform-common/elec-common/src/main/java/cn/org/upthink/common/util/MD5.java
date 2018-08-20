package cn.org.upthink.common.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author LEE.SIU.WAH
 * @version 1.0
 */
public final class MD5 {

	private static final char[] DIGITS_LOWER;
	private static final char[] DIGITS_UPPER;

	public static final String SALT_DEFAULT = "kangmei@2018";

	static{
		DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	}

	public static String getMD5(String source) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(source.getBytes());
		byte[] md5Bytes = md.digest();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < md5Bytes.length; i++) {
			int t = md5Bytes[i] & 0xff;
			if (t <= 0xf) {
				res.append("0");
			}
			res.append(Integer.toHexString(t));
		}
		return res.toString();
	}

	public static String getMD5(String source, String salt){
		if(salt == null || "".equalsIgnoreCase(salt)){
			salt = SALT_DEFAULT;
		}
		return digest(source.getBytes(), salt.getBytes(), 1024);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static String digest(byte[] input, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return new String(encodeHex(result));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * from: org.apache.commons.codec.binary.Hex
	 * @param data
	 * @param toDigits
	 * @return
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		int i = 0;

		for(int var5 = 0; i < l; ++i) {
			out[var5++] = toDigits[(240 & data[i]) >>> 4];
			out[var5++] = toDigits[15 & data[i]];
		}

		return out;
	}

}
