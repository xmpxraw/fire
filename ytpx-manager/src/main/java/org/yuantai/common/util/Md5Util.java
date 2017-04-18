package org.yuantai.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Md5处理工具类
 * @author zhanngle
 */
public class Md5Util {

	private static final Logger logger = LogManager.getLogger(Md5Util.class);

	/**
	 * 为输入的字符串生成MD5码
	 */
	public static String toMd5(String str) {

		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("参数不能为空!");
		}

		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("生成MD5码出错",e);
		}

		String code = hexString.toString().toUpperCase();
		return code;
	}
	
	/**
	 * 生成用户密码
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String makePassword(String password,String salt) {
		return toMd5(toMd5(password)+salt);
	}
	
	/**
	 * 随机生成UUID值
	 * @return
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
