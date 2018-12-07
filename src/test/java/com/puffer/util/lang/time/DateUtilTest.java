package com.puffer.util.lang.time;

import java.util.Date;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * DateUtil单元测试类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:07:41
 */
public class DateUtilTest {

	/**
	 * 格式化日期单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:34:11
	 */
	@Test
	public void formatDate() {
		String date = DateUtil.formatDate(new Date());
		System.out.println(date);
	}

	/**
	 * 格式化时分秒
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:48:16
	 */
	@Test
	public void formatTime() {
		String date = DateUtil.formatTime(new Date());
		System.out.println(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:48:27
	 */
	@Test
	public void formatDateTime() {
		String date = DateUtil.formatDateTime(new Date());
		System.out.println(date);
	}

	/**
	 * 转换日期字符串为时间单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:48:45
	 */
	@Test
	public void parseDate() {
		System.out.println(DateUtil.parseDate("2017-11-20"));
		System.out.println(DateUtil.parse("20171121", "yyyyMMdd"));
	}

	/**
	 * 转换日期时间单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:49:07
	 */
	@Test
	public void parseDateTime() {
		System.out.println(DateUtil.parseDateTime("2017-11-20 21:12:12"));
		System.out.println(DateUtil.parse("20171121 122112", "yyyyMMdd HHmmss"));
	}

	/**
	 * 计算两个时间的天数间隔
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:46:28
	 */
	@Test
	public void distanceDays() {
		Date date = new Date();
		Assert.assertEquals(0, DateUtil.distanceDays(date, date));
		Assert.assertEquals(-1, DateUtil.distanceDays(DateUtil.addDays(date, 1), date));
		Assert.assertEquals(1, DateUtil.distanceDays(date, DateUtil.addDays(date, 1)));
	}

	/**
	 * 指定时间是否在区间内，单元测试
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:46:08
	 */
	@Test
	public void isContains() {
		Date date = new Date();
		boolean contains = DateUtil.isContains(DateUtil.addSeconds(date, -1), DateUtil.addSeconds(date, 1), date);
		Assert.assertTrue(contains);

		contains = DateUtil.isContains(DateUtil.addSeconds(date, -1), date, date);
		Assert.assertFalse(contains);

		contains = DateUtil.isContains(date, DateUtil.addSeconds(date, 1), date);
		Assert.assertTrue(contains);

		contains = DateUtil.isContains(date, date, date);
		Assert.assertFalse(contains);
	}

	@Test
	public void getNowMax() {

		DateTime actDate = new DateTime();

		System.out.println(DateUtil.getNowMax());
	}
}
