package com.aide.financial.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 1 format Date ——> String
 * 2 parse String ——> Date
 * 3 获取年月日、星期等
 */
public class DateUtils {

	// 日期格式 (可按需添加)
	@NonNull
    public static String DEFAULT_PATTERN = "yyyy-MM-dd";
	@NonNull
    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	@NonNull
    public static String TIMES_PATTERN = "HH:mm:ss";
	@NonNull
    public static String DIR_PATTERN = "yyyy/MM/dd/";
	@NonNull
    public static String SEQUENCE_PATTERN = "yyyyMMddhhmmss";

	/**
	 * format Date ——> String
	 * @param date
	 * @param format 必须使用指定格式，不可自己胡乱定义
	 * @return string
	 */
	public static String formatDate(@Nullable Date date, @Nullable String format) {
		String result = "";
		if (date != null && format != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}
		return result;
	}

	public static String formatDateByDefault(Date date) {
		return formatDate(date, DEFAULT_PATTERN);
	}

	public static String formatDateByTimeStamp(Date date) {
		return formatDate(date, TIMESTAMP_PATTERN);
	}

	/**
	 * parse String ——> Date
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, @NonNull String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date nowDate = sdf.parse(dateStr);// 抛出异常
		return nowDate;
	}

	public static Date parseDateByDefault(String date) throws ParseException {
		return parseDate(date, DEFAULT_PATTERN);
	}

	public static Date parseDateByTimeStamp(String date) throws ParseException {
		return parseDate(date, TIMESTAMP_PATTERN);
	}

	/**
	 * 获取当前时间
	 */
	@NonNull
    public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取年份
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取星期
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}
	
	public static char getWeek2Char(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		switch (dayOfWeek) {
		case 0:
			return '7';
		case 1:
			return '1';
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		default:
			return ' ';
		}
	}

	/**
	 * 获取日期
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间(小时)
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前时间(分)
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间(秒)
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前毫秒
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

}
