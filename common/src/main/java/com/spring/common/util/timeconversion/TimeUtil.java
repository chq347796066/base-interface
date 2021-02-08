package com.spring.common.util.timeconversion;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类：时间操作类
 * 
 * @author lenovo
 */
public class TimeUtil {
	public final static int SECOND = 0;
	public final static int MINUTE = 1;
	public final static int HOUR = 2;
	public final static int DAY = 3;
	public final static int MONTH = 4;

	/**
	 * 将字符串转固定格式的日期类型 这个方法不会抛出异常，不建议使用，建议使用convertStringEx
	 * 
	 * @param value
	 * @param format
	 * 
	 */
	@Deprecated
	public static Date convertString(String value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 这个方法不会抛出异常，不建议使用，建议使用convertStringEx 将字符串转日期类型，格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value
	 * 
	 */
	@Deprecated
	public static Date convertString(String value) {
		return convertString(value, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * <pre>
	 * 这个方法会抛出异常
	 * </pre>
	 * 
	 * @param value
	 * 
	 * @throws Exception
	 */
	public static Date convertStringEx(String value) throws Exception {
		return convertStringEx(value, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * <pre>
	 * 这个方法会抛出异常
	 * </pre>
	 * 
	 * @param value
	 * @param format
	 * 
	 * @throws Exception
	 */
	public static Date convertStringEx(String value, String format)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return sdf.parse(value);
	}

	/**
	 * 得到当前时间，例如"2002-11-06 17:08:59"
	 * 
	 * 
	 */
	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}

	/**
	 * 根据长整型毫秒数返回时间形式:如:2007-01-23 13:45:21
	 *
	 * @param millseconds
	 *
	 */
	public static String getDateTimeString(long millseconds) {

		return getDate(millseconds, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 取得yyyyMMdd格式的时间
	 *
	 * @param time
	 *
	 */
	public static String getDayDate(long time) {
		return getDate(time, "yyyyMMdd");
	}

	public static String getDate(long time, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date(time));
	}

	/**
	 * 取得时间
	 *
	 * @param date
	 *
	 */
	public static String getDateTimeString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 取得时间
	 *
	 * @param date
	 * @param format
	 *
	 */
	public static String getDateTimeString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 比较两个时间大小 结束时间是否大于开始时间
	 *
	 * @param startTime
	 * @param endTime
	 *
	 */
	@SuppressWarnings("unused")
	public static boolean isTimeLarge(String startTime, String endTime) {
		try {
			String tmp = "";
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = formatter.parse(startTime, pos);
			Date dt2 = formatter.parse(endTime, pos1);
			long lDiff = dt2.getTime() - dt1.getTime();
			return lDiff > 0;
		} catch (Exception e) {

			return false;
		}
	}

	public static long getTime(Date startTime, Date endTime) {
		return endTime.getTime() - startTime.getTime();
	}

	/**
	 * 取得日期
	 *
	 * @param date
	 *
	 */
	public static String getDateString(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		return "";
	}

	public static String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}

	/**
	 * 得到当前日期，例如"2002-11-06"
	 *
	 *
	 */
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd");
	}

    /**
     * 得到当前日期的后一天String类型
     * 例2013-01-31加上一到为2013-02-01
     * @throws ParseException
     *
     */
    public static String getNextDay(String date) {
    	try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dd;

			dd =format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			return format.format(calendar.getTime());
		} catch (ParseException e) {
			return "-";
		}

    }

	/**
	 * 得到当前日期的前一天String类型
	 * 例2013-02-01的上一天为2013-01-31
	 * @throws ParseException
	 *
	 */
	public static String getLastDay(String date) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dd;

			dd =format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH, -1);

			return format.format(calendar.getTime());
		} catch (ParseException e) {
			return "-";
		}

	}

	/**
	 * 根据长整型毫秒数返回日期形式:如:2007-01-23
	 *
	 * @param millseconds
	 *
	 */
	public static String getDateString(long millseconds) {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				millseconds));
	}

	/**
	 * 根据指定格式取得日期字符格式。
	 *
	 * @param formater
	 *
	 */
	public static String getDateString(String formater) {
		return new SimpleDateFormat(formater).format(new Date());
	}

	/**
	 * 取得当前日期的毫秒数
	 *
	 *
	 */
	public static long getMillsByToday() {
		String str = getDateString("yyyy-MM-dd");
		str = String.valueOf(getMillsByDateString(str));
		return Long.parseLong((str.substring(0, str.length() - 3) + "000"));
	}

	/**
	 * 取得多少天后的数据。
	 *
	 * @param days
	 *
	 */
	public static long getNextDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		String str = String.valueOf(cal.getTimeInMillis());
		return Long.parseLong((str.substring(0, str.length() - 3) + "000"));
	}

	/**
	 * 取得下一天。
	 *
	 * @param date
	 * @param days
	 *
	 */
	public static Date getNextDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 根据给出的日期字符串返回该日期的长整型毫秒数(字符串前后空格不计) 如果strDate的格式不正确将返回0
	 *
	 * @param strDate
	 *            形如:2007-01-23的日期字符串
	 *
	 */
	public static long getMillsByDateString(String strDate) {
		Calendar cal = Calendar.getInstance();
		if (strDate != null && strDate.trim().length() > 9) {
			strDate = strDate.trim();
			try {
				int year = Integer.parseInt(strDate.substring(0, 4));
				int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
				int date = Integer.parseInt(strDate.substring(8, 10));
				cal.set(year, month, date, 0, 0, 0);
				String str = String.valueOf(cal.getTimeInMillis());
				return Long
						.parseLong((str.substring(0, str.length() - 3) + "000"));

			} catch (Exception e) {

			}
		}

		return 0;
	}

	/**
	 * 根据给出的时间字符串返回该时间的长整型毫秒数(字符串前后空格不计) 如果strDateTime的格式不正确将返回0
	 *
	 * @param strDateTime
	 *            形如:2007-01-23 13:45:21的时间字符串
	 *
	 */
	public static long getMillsByDateTimeString(String strDateTime) {
		Calendar cal = Calendar.getInstance();
		if (strDateTime != null && strDateTime.trim().length() > 18) {
			strDateTime = strDateTime.trim();
			try {
				int year = Integer.parseInt(strDateTime.substring(0, 4));
				int month = Integer.parseInt(strDateTime.substring(5, 7)) - 1;
				int date = Integer.parseInt(strDateTime.substring(8, 10));
				int hour = Integer.parseInt(strDateTime.substring(11, 13));
				int minute = Integer.parseInt(strDateTime.substring(14, 16));
				int second = Integer.parseInt(strDateTime.substring(17, 19));
				cal.set(year, month, date, hour, minute, second);
				return cal.getTimeInMillis();
			} catch (Exception e) {

			}
		}

		return 0;
	}

	/**
	 * 根据长整型毫秒数返回时间形式:
	 *
	 * @param millseconds
	 *
	 */
	public static String getFormatString(long millseconds, String format) {
		if (format == null || format.trim().length() == 0) {
			format = "yyyy-MM-dd";
		}
		format = format.trim();
		return new SimpleDateFormat(format).format(new Date(
				millseconds));
	}

	/**
	 * 得到当前的毫秒时间
	 *
	 *
	 */
	public static long getCurrentTimeMillis() {
		Calendar c = Calendar.getInstance();
		return c.getTimeInMillis();
	}

	/**
	 * 日期格式化
	 *
	 * @param obj
	 * @param style
	 *
	 * @throws Exception
	 */
	public static String dateFormat(Object obj, String style) throws Exception {
		Date date = toDate(obj);
		if (null == date) {
			return null;
		}
		if (null == style || "".equals(style)) {
			style = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(style).format(date);
	}

	/**
	 * 将各种数据类型转换为Date,不符合日期格式的字符串或数字引起的异常将返回null;
	 *
	 * @param obj
	 *
	 */
	@SuppressWarnings("deprecation")
	public static Date toDate(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Long) {
			return new Date((Long) obj);
		} else if (obj instanceof Integer) {
			return new Date(Long.valueOf(obj.toString()));
		} else if (obj instanceof String) {
			String val = (String) obj;
			try {
				return new Date(Long.parseLong(val));
			} catch (Throwable t) {
				try {
					return new Date(val.replace("-", "/"));
				} catch (Throwable e) {
					e.getStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * 根据millsecond时间取得 如 yyyy-MM-dd HH:mm:ss 的时间,如果时分秒都为0就返回 yyyy-MM-dd
	 *
	 * @param mills
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String getTimeByMills(long mills) throws Exception {
		try {
			if (mills == 0) {
				return "-";
			}
			Date date = null;
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(mills);
			date = ca.getTime();
			SimpleDateFormat myformat;

			if (ca.get(Calendar.HOUR_OF_DAY) == 0 && ca.get(Calendar.MINUTE) == 0
					&& ca.get(Calendar.SECOND) == 0) {
				myformat = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			return myformat.format(date);
		} catch (Exception e) {
			return "-";
		}
	}

	/**
	 * 将长整型的日期转换成为yyyy-MM-dd格式的日期。
	 *
	 * @param mills
	 *
	 * @throws Exception
	 */
	public static String formatDate(long mills) throws Exception {
		try {
			if (mills == 0) {
				return "-";
			}
			Date date = null;
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(mills);
			date = ca.getTime();
			SimpleDateFormat myformat;

			myformat = new SimpleDateFormat("yyyy-MM-dd");

			return myformat.format(date);
		} catch (Exception e) {
			return "-";
		}
	}

	/**
	 * 将长整型的日期转换成为yyyy-MM-dd HH:mm:ss格式的日期。
	 *
	 * @param mills
	 *
	 * @throws Exception
	 */
	public static String formatTime(long mills) throws Exception {
		try {
			if (mills == 0) {
				return "-";
			}
			Date date = null;
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(mills);
			date = ca.getTime();
			SimpleDateFormat myformat;

			myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			return myformat.format(date);
		} catch (Exception e) {
			return "-";
		}
	}

	/**
	 * 根据时间格式如 yyyy-MM-dd HH:mm:ss 的时间取得 UTC 时间
	 *
	 * @param 格式如
	 *            的字符串yyyy-MM-dd HH:mm:ss 或者 yyyy-mm-dd long Utc Millisecond 时间
	 */
	public static long getMillsByTime(String strTime) throws Exception {
		try {
			int year, month, day, hour, minute, second;
			if (strTime.length() != 19 && strTime.length() != 10) {
				throw new Exception("the time string is wrong.");
			}

			year = Integer.parseInt(strTime.substring(0, 4));
			month = Integer.parseInt(strTime.substring(5, 7)) - 1;
			day = Integer.parseInt(strTime.substring(8, 10));

			if (year < 1000 || year > 3000) {
				throw new Exception("the year is wrong.");
			}

			if (month < 0 || month > 12) {
				throw new Exception("the month is wrong.");
			}

			if (day < 1 || day > 31) {
				throw new Exception("the day is wrong");
			}

			Calendar ca = Calendar.getInstance();
			if (strTime.length() == 19) {
				hour = Integer.parseInt(strTime.substring(11, 13));
				minute = Integer.parseInt(strTime.substring(14, 16));
				second = Integer.parseInt(strTime.substring(17, 19));

				if (hour < 0 || hour > 24) {
					throw new Exception("the hour is wrong.");
				}

				if (minute < 0 || minute > 60) {
					throw new Exception("the minute is wrong.");
				}

				if (second < 0 || second > 60) {
					throw new Exception("the second is wrong.");
				}

				ca.set(year, month, day, hour, minute, second);
			} else if (strTime.length() == 10) {
				ca.set(year, month, day, 0, 0, 0);
			}
			return ca.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 根据时间单位，时间间隔，当前时间计算出指定时间加上时间间隔后的时间
	 *
	 * @param TimeUnit
	 *            请使用TimeUtil的静态常量
	 * @param interval
	 *            可为负值
	 * @param timeMill
	 *
	 */
	public static long getNextTime(int timeUnit, int interval, long timeMill) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(timeMill);
		switch (timeUnit) {
		case TimeUtil.SECOND:
			ca.add(Calendar.SECOND, interval);
			break;
		case TimeUtil.MINUTE:
			ca.add(Calendar.MINUTE, interval);
			break;
		case TimeUtil.HOUR:
			ca.add(Calendar.HOUR, interval);
			break;
		case TimeUtil.DAY:
			ca.add(Calendar.DATE, interval);
			break;
		case TimeUtil.MONTH:
			ca.add(Calendar.MONTH, interval);
			break;
		default:
			return 0;
		}
		return ca.getTimeInMillis();
	}

	/**
	 * 根据时间字符串返回date类型
	 *
	 *
	 * @throws ParseException
	 */
	public static Date getDateTimeByTimeString(String timeString) {
		DateFormat f = new SimpleDateFormat(
				"yy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = f.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据日期字符串返回date类型。
	 *
	 * @param timeString
	 *
	 */
	public static Date getDateByDateString(String timeString) {
		DateFormat f = new SimpleDateFormat("yy-MM-dd");
		Date date = new Date();
		try {
			date = f.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 取得日期。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int day) {
		year = year - 1900;
		month = month - 1;
		Date d = new Date(year, month, day);
		return d;
	}

	/**
	 * 取得日期。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int day, int hour,
			int minute) {
		year = year - 1900;
		month = month - 1;
		Date d = new Date(year, month, day, hour, minute);
		return d;
	}

	/**
	 * 取得结束时间和开始时间的秒数。
	 * 
	 * @param endTime
	 *            结束时间
	 * @param startTime
	 *            开始时间
	 * 
	 */
	public static int getSecondDiff(Date endTime, Date startTime) {
		long start = startTime.getTime();
		long end = endTime.getTime();
		return (int) ((end - start) / 1000);
	}

	/**
	 * 取得一个月的天数
	 * 
	 * @param year
	 *            实际年份
	 * @param mon
	 *            实际月份 1到12月
	 * 
	 */
	public static int getDaysOfMonth(int year, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mon - 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 取得某月第一天为星期几。<br>
	 * 星期天为1。 星期六为7。
	 * 
	 * @param year
	 * @param mon
	 * 
	 */
	public static int getWeekDayOfMonth(int year, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon - 1, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据长整形的毫秒数返回两位小树类型的小时数（不带单位）
	 *
	 * @param millseconds
	 *
	 */
	public static String getDurationHour(Long millseconds) {
		String time = "0";

		if (millseconds == null || millseconds == 0) {
			return time;
		} else if (millseconds > 0) {
			double h = (millseconds * 100 / 1000 / 60 / 60);
			double dHours2v = h / 100;
			double dTemp = Math.floor(dHours2v);
			double dResult = dHours2v - dTemp;

			if (dHours2v > 0) {
				if (dResult > 0d) {
					time = dHours2v + "";
				} else {
					time = (long) dTemp + "";
				}
			} else {
				time = "0";
			}
		}
		return time;
	}
}