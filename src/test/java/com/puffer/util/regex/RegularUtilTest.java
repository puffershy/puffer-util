package com.puffer.util.regex;


import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 正则表达式单元测试
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017上午10:06:39
 */
public class RegularUtilTest {

	/**
	 * 邮箱验证
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午10:09:18
	 */
	@Test
	public void isEmail() {
		Assert.assertTrue(RegularUtil.isEmail("puffershy@gmail.com"));
		Assert.assertTrue(RegularUtil.isEmail(" puffershy@gmail.com"));
		Assert.assertTrue(RegularUtil.isEmail("  puffershy@gmail.com"));
		Assert.assertTrue(RegularUtil.isEmail("puffershy@gmail.163.com"));
		Assert.assertTrue(RegularUtil.isEmail("puffershy@gmail.163.com"));
		Assert.assertTrue(RegularUtil.isEmail("puffer1shy@gmail.163.com"));
		Assert.assertTrue(RegularUtil.isEmail("puffer1-shy@gmail.163.com"));
		Assert.assertTrue(RegularUtil.isEmail("puffer1_shy@gmail.163.com"));

		Assert.assertFalse(RegularUtil.isEmail("puffe!rshy@gmail.163.com"));
	}

	/**
	 * 中文姓名单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午10:25:27
	 */
	@Test
	public void isChinessName() {
		Assert.assertTrue(RegularUtil.isChinessName("布衣"));
		Assert.assertTrue(RegularUtil.isChinessName("布衣·余"));
		Assert.assertTrue(RegularUtil.isChinessName("余䶮"));

		Assert.assertFalse(RegularUtil.isChinessName("1布衣"));
		Assert.assertFalse(RegularUtil.isChinessName("布衣a"));
	}

	@Test
	public void isDateTime() {
		// String dateTime = "2017-11-29 12:12:12";
		// boolean result = RegularUtil.isDateTime(dateTime);
		// Assert.assertTrue(result);
		Assert.assertTrue(RegularUtil.isDateTime("2017-11-29 12:12:12"));
		Assert.assertTrue(RegularUtil.isDateTime("2017-11-29 54:12:12"));
	}
}
