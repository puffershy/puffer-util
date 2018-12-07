package com.puffer.util.lang.time;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * <p>
 * 日期工具类
 * </p>
 * 
 * <p>
 * JDK本身自带的SimpleDateFormat是一个非线程安全,且新建对象很重<br>
 * org.apache.commons.lang.time.FastDateFormat 与
 * org.apache.commons.lang.time.DateFormatUtils的format()只支持long，Date，Calendar类型的输入，且不存在parse()方法<br>
 * <p>
 * 该工具类使用Joda-Time类库实现时间的处理
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午2:36:10
 */
public class DateUtil {

	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String FORMAT_DATETIME = FORMAT_DATE + " " + FORMAT_TIME;
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 格式化转换日期
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:08:53
	 * @param date
	 * @return 返回格式化后的日期字符串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, FORMAT_DATE);
	}

	/**
	 * 格式化转换日期
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:13:04
	 * @param date
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		LocalDate localDate = new LocalDate(date);
		return localDate.toString(pattern);
	}

	/**
	 * 格式化转换时间
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:12:04
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return formatTime(date, FORMAT_TIME);
	}

	/**
	 * 格式化转换时间
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:13:53
	 * @param date
	 * @param pattern
	 *            时间格式
	 * @return
	 */
	public static String formatTime(Date date, String pattern) {
		LocalTime localTime = new LocalTime(date);
		return localTime.toString(pattern);
	}

	/**
	 * 格式化转换时间戳
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:30:01
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return formatDateTime(date, FORMAT_DATETIME);
	}

	/**
	 * 格式化转换时间戳
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:29:08
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(Date date, String pattern) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(pattern);
	}

	/**
	 * 转换日期字符串
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:44:00
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date, FORMAT_DATE);
	}

	/**
	 * 转换时间字符串
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:50:33
	 * @param dateTime
	 * @return
	 */
	public static Date parseDateTime(String dateTime) {
		return parse(dateTime, FORMAT_DATETIME);
	}

	/**
	 * 转换时间字符串
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:42:48
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateTime, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(dateTime, format).toDate();
	}

	/**
	 * 操作月份
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:38:31
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMonths(Date date, int value) {
		DateTime dateTime = new DateTime(date);
		if (value < 0) {
			// 如果值是负数，则减少月份
			return dateTime.minusMonths(Math.abs(value)).toDate();
		} else {
			// 如果值是正数，则增加月份
			return dateTime.plusMonths(value).toDate();
		}
	}

	/**
	 * 操作天数
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:36:18
	 * @param date
	 * @param value
	 *            天数，正数-增加天数；负数-减少天数
	 * @return
	 */
	public static Date addDays(Date date, int value) {
		DateTime dateTime = new DateTime(date);
		if (value < 0) {
			// 如果值是负数，则减少天数
			return dateTime.minusDays(Math.abs(value)).toDate();
		} else {
			// 如果值是正数，则增加天数
			return dateTime.plusDays(value).toDate();
		}

	}

	/**
	 * 操作小时
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:43:54
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addHours(Date date, int value) {
		DateTime dateTime = new DateTime(date);
		if (value < 0) {
			// 如果值是负数，则减少小时
			return dateTime.minusHours(Math.abs(value)).toDate();
		} else {
			// 如果值是正数，则增加小时
			return dateTime.plusHours(value).toDate();
		}

	}

	/**
	 * 操作分钟
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:44:02
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMinute(Date date, int value) {
		DateTime dateTime = new DateTime(date);
		if (value < 0) {
			// 如果值是负数，则减少分钟
			return dateTime.minusMinutes(Math.abs(value)).toDate();
		} else {
			// 如果值是正数，则增加分钟
			return dateTime.plusMinutes(value).toDate();
		}

	}

	/**
	 * 操作秒
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:44:18
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addSeconds(Date date, int value) {
		DateTime dateTime = new DateTime(date);
		if (value < 0) {
			// 如果值是负数，则减少秒数
			return dateTime.minusSeconds(Math.abs(value)).toDate();
		} else {
			// 如果值是正数，则增加秒数
			return dateTime.plusSeconds(value).toDate();
		}

	}

	/**
	 * 计算区间天数
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:58:54
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int distanceDays(Date begin, Date end) {
		return new Period(begin.getTime(), end.getTime(), PeriodType.days()).getDays();
	}

	/**
	 * 计算指定日期是否在该区间内<br>
	 * [beginDate, endDate)
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:03:11
	 * @param begin
	 * @param end
	 * @param targetDate
	 * @return begin <= targetDate < end 返回true;否则返回false;
	 */
	public static boolean isContains(final Date begin, final Date end, final Date targetDate) {
		Interval interval = new Interval(begin.getTime(), end.getTime());
		return interval.contains(targetDate.getTime());
	}

	/**
	 * 获取当前日期的结束时间
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018上午11:13:54
	 * @return
	 */
	public static Date getNowMax() {
		DateTime nowTime = new DateTime();
		// DateTime endOfDay = nowTime.millisOfDay().withMaximumValue();
		return nowTime.millisOfDay().withMaximumValue().toDate();
	}

}
