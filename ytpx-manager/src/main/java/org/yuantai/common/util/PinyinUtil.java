package org.yuantai.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;

/**
 * 中文拼音工具类
 * @ClassName: PinyinUtil
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2014年11月26日 下午5:24:06
 */
public class PinyinUtil {
	private static Logger logger = Logger.getLogger(PinyinUtil.class.getName());

	/**
	 * 获得此汉字的拼音
	 * @param hanzhi
	 * @return
	 */
	public static String getPinYin(String hanzhi) {
		return getPinYin(hanzhi, false);
	}

	/**
	 * 获得此汉字的拼音首字母
	 * @param hanzhi
	 * @return
	 */
	public static String getPinYinHeadChar(String hanzhi) {
		return getPinYin(hanzhi, true);
	}
	
	/**
	 * 获得第一个汉字的拼音首字母
	 * @param hanzhi
	 * @return
	 */
	public static String getFirstPinYinHeadChar(String hanzhi) {
		String c=getPinYin(hanzhi.substring(0,1), true);
		return c;
	}

	private static String getPinYin(String hanzhi, boolean isHeadChar) {
		int len = hanzhi.length();
		char[] cs = hanzhi.toCharArray();

		// 设置输出格式
		HanyuPinyinOutputFormat formatParam = new HanyuPinyinOutputFormat();
		formatParam.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		formatParam.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		formatParam.setVCharType(HanyuPinyinVCharType.WITH_V);

		StringBuilder py = new StringBuilder();
		Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]{0,128}$");
		for (int i = 0; i < len; i++) {
			char c = cs[i];
			Matcher matcher = pattern.matcher(String.valueOf(c));
			// 检查是否是汉字,如果不是汉字就不转换
			if (!matcher.matches()) {
				py.append(c);
				continue;
			}
			// 对汉字进行转换成拼音
			try {
				String[] t2 = PinyinHelper.toHanyuPinyinStringArray(c,
						formatParam);
				if (isHeadChar) {
					py.append(t2[0].charAt(0));
				} else {
					py.append(t2[0]);
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.error(c + " to pinyin error!", e);
				py.append(c);
			}
		}

		return py.toString();
	}
}
