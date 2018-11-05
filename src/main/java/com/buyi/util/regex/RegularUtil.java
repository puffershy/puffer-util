package com.buyi.util.regex;

import static com.buyi.util.regex.RegularExpression.CH_NAME_REGEX;
import static com.buyi.util.regex.RegularExpression.DATE_FORMAT_DATETIME;
import static com.buyi.util.regex.RegularExpression.EMAIL_REGEX;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 正则工具类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午4:52:19
 */
public class RegularUtil {

	/**
	 * 正则表达式解释器缓存，key-正则表达式规则
	 */
	private static final Map<String, Pattern> patternFactory = Maps.newHashMap();

	/**
	 * 获取表达式相应正则解释器
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午10:01:44
	 * @param regularExpression
	 * @return
	 */
	private static Pattern getPattern(String regularExpression) {
		if (patternFactory.get(regularExpression) == null) {
			synchronized (patternFactory) {
				if (patternFactory.get(regularExpression) == null) {
					Pattern pattern = Pattern.compile(regularExpression);
					patternFactory.put(regularExpression, pattern);
				}
			}
		}

		return patternFactory.get(regularExpression);
	}

	/**
	 * 校验邮箱合法性
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午8:52:20
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return match(email, EMAIL_REGEX);
	}

	/**
	 * 判断中文姓名
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午8:53:01
	 * @param name
	 * @return
	 */
	public static boolean isChinessName(String name) {
		return match(name, CH_NAME_REGEX);
	}

	public static boolean isDateTime(String dateTime) {
		return match(dateTime, DATE_FORMAT_DATETIME);
	}

	/**
	 * 校验中文姓名，GBK方式<br>
	 * GBK编码依然采用双字节编码方案，其编码范围：8140－FEFE，剔除xx7F码位，共23940个码位。
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:02:28
	 * @param name
	 * @return
	 */
	// public static boolean isGbk(String name) {
	// char[] chars = name.toCharArray();
	// if (chars.length < 1) {
	// return false;
	// }
	//
	// try {
	// boolean isGBK = true;
	//
	// for (int i = 0; i < chars.length; i++) {
	// // 转换成GBK字节
	// byte[] bytes = String.valueOf(chars[i]).getBytes("GBK");
	//
	// if (bytes.length == 2) {
	// int[] ints = new int[2];
	// ints[0] = bytes[0] & 0xff;
	// ints[1] = bytes[1] & 0xff;
	// if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <=
	// 0xFE) {
	// isGBK = isGBK && true;
	// }
	// } else {
	// isGBK = false;
	// }
	// }
	//
	// return isGBK;
	// } catch (UnsupportedEncodingException e) {
	// throw new IdentifyException("无法识别的编码");
	// }
	//
	// }

	/**
	 * 根据指定正则表达式匹配指定值，并放回匹配结果
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午5:00:50
	 * @param value
	 * @param regex
	 * @return true表示匹配命中；false表示不匹配
	 */
	private static boolean match(String value, String regex) {
		if (StringUtils.isBlank(value)) {
			return false;
		}

		Pattern pattern = getPattern(regex);
		return pattern.matcher(value).matches();
	}

}
