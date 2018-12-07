package com.puffer.util.regex;

/**
 * 正则表达式常量
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午5:23:11
 */
public class RegularExpression {
	/**
	 * 邮箱正则表达式<br>
	 * 规则：只允许英文字母、数字、下划线、英文句号、以及中划线组成
	 * [\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?
	 * 
	 */
	public static final String EMAIL_REGEX = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

	/**
	 * 身份证正则表达式<br>
	 * 18位身份证校验
	 */
	public static final String ID_CARD_REGEX = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";

	/**
	 * 银行卡卡号正则表达式
	 */
	public static final String BANK_CARD_REGEX = "^(\\d{4} ?){2,5}\\d{2,4}$";

	/**
	 * 手机号码正则表达式
	 */
	public static final String PHONE_REGEX = "^0?1[34578]\\d{9}$";

	/**
	 * 中文姓名正则表达式<br>
	 * 中文姓名存在以下几种场景：
	 * 
	 * <pre>
	 * 张三
	 * 张三·李四
	 * 张三·李四·王五
	 * </pre>
	 * 
	 * 不考虑生僻字：^[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{1,5})*$<br>
	 * 考虑生僻字，该表达式只能在java环境：^[\\p{script=Han}]{2,5}(?:·[\\p{script=Han}]{1,5})*$
	 */
	public static final String CH_NAME_REGEX = "^[\\p{script=Han}]{2,5}(?:·[\\p{script=Han}]{1,5})*$";

	/**
	 * 数字正则表达式
	 */
	public static final String NUMBER_REGEX = "";

	/**
	 * 正整数
	 */
	public static final String NUMBER_POSITIVE_INTEGER = "^[1-9][0-9]{0,}$";

	/**
	 * 整数,包含正数和负数
	 */
	public static final String NUMBER_INTEGER = "^\\-?[0-9]+$";

	/**
	 * 时间日期格式表达式<br>
	 * 格式：yyyy-MM-dd
	 * ([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))
	 */
	public static final String DATE_FORMAT_DATE = "";

	/**
	 * 时间格式表达式<br>
	 * 格式：yyyy-MM-dd HH:mm:ss ([0-9]{4}[-][0-9]{2}[-][0-9]{1,2}[
	 * ][0-9]{2}[:][0-9]{2}[:][0-9]{2})
	 * 
	 */
	public static final String DATE_FORMAT_DATETIME = "([0-9]{4}[-][0-9]{2}[-][0-9]{1,2}[ ][0-9]{2}[:][0-9]{2}[:][0-9]{2})";
}
