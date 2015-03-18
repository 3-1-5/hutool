package com.xiaoleilu.hutool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.xiaoleilu.hutool.exceptions.UtilException;

/**
 * 安全相关工具类
 * 
 * @author xiaoleilu
 *
 */
public class SecureUtil {

	public final static String MD2 = "MD2";
	public final static String MD4 = "MD4";
	public final static String MD5 = "MD5";
	
	public final static String SHA1 = "SHA-1";
	public final static String SHA256 = "SHA-256";
	
	public final static String RIPEMD128 = "RIPEMD128";
	public final static String RIPEMD160 = "RIPEMD160";

	/**
	 * md5编码
	 * 
	 * @param source 需要计算md5的byte数组
	 * @return MD5
	 */
	public static String md5(byte[] source) {
		String s = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
		} catch (Exception e) {
			throw new UtilException("Get MD5 error!", e);
		}
		return s;
	}

	/**
	 * md5编码
	 * 
	 * @param source 字符串
	 * @param charset 字符集，为空使用系统默认字符集
	 * @return md5
	 */
	public static String md5(String source, String charset) {
		if (source == null) {
			return null;
		}

		if (StrUtil.isBlank(charset)) {
			return md5(source.getBytes());
		}

		try {
			return md5(source.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new UtilException("Unsupported encoding: " + charset, e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param source 被加密的字符串
	 * @param algorithmName 算法名
	 * @param charset 字符集
	 * @return 被加密后的值
	 */
	public static String encrypt(String source, String algorithmName, String charset) {
		final byte[] bt = StrUtil.encode(source, charset);
		MessageDigest md = null;
		try {
			if (StrUtil.isBlank(algorithmName)) {
				algorithmName = "MD5";
			}
			md = MessageDigest.getInstance(algorithmName);
		} catch (NoSuchAlgorithmException e) {
			throw new UtilException(StrUtil.format("No such algorithm name for: {}", algorithmName));
		}
		md.update(bt);
		return Conver.toHex(md.digest());
	}
	
	/**
	 * SHA-1算法加密
	 * @param source 被加密的字符串
	 * @param charset 字符集
	 * @return 被加密后的字符串
	 */
	public String sha1(String source, String charset){
		return encrypt(source, SHA1, charset);
	}
}
