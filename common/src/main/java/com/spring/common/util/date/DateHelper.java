
package com.spring.common.util.date;

import com.spring.common.util.string.StrUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:时间工具类
*/
public class DateHelper {

	/**
	 * 缺省的日期各个部分之间的连结符号
	 */
	private String markDate = "-";
	private int markDateLen = 1;

	/**
	 * 缺省的时间各个部分之间的连结符号
	 */
	private String markTime = ":";

	private int markTimeLen = 1;

	private String dtspace = " ";
	private int dtspaceLen = 1;

	private int longDateLen = 10;
	private int medDateLen = 8;

	private int longTimeLen = 8;
	private int medTimeLen = 5;

	private int longDatetimeLen = 19;

	private int medDatetimeLen = 14;

	/**
     * 每周7天
     */
	public static final int DAYS_PER_WEEK = 7;

	/**
	 * 日期段的分隔类型：年
	 */
	public static final int DATE_SEG_YEAR = 1;

	/**
	 * 日期段的分隔类型：月
	 */
	public static final int DATE_SEG_MONTH = 2;

	/**
	 * 日期段的分隔类型：日
	 */
	public static final int DATE_SEG_DAY = 3;

	/**
	 * 在普通的一年中，各个月份的天数
	 */
	public static final int[] DAYS_OF_MONTH_IN_NORMAL_YEAR = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 在闰年中，各个月份的天数
	 */
	public static final int[] DAYS_OF_MONTH_IN_LEAP_YEAR = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 按照缺省格式构造一个日期操作助手
	 */
	public DateHelper()
	{
	}

	/**
	 * 构造一个格式化的日期操作助手
	 * 
	 * @param vDateMark
	 *            日期的间隔符号
	 * @param vTimeMark
	 *            时间的价格符号
	 * @param vDtSpace
	 *            日期和时间之间的间隔符号
	 */
	public DateHelper(String vDateMark, String vTimeMark, String vDtSpace)
	{
		markDate = vDateMark;
		markDateLen = vDateMark.length();

		markTime = vTimeMark;
		markTimeLen = vTimeMark.length();

		dtspace = vDtSpace;
		dtspaceLen = dtspace.length();

		updateVar();
	}

	// public static String dateToStr(Date date, String datePattern)
	// {
	// SimpleDateFormat df = new SimpleDateFormat(datePattern);
	// return df.format(date);
	// }

	/**
	 * 在格式改变的时候,重新计算各个部分的长度
	 */
	private void updateVar()
	{
		longDateLen = 4 + markDateLen + 2 + markDateLen + 2;
		medDateLen = 2 + markDateLen + 2 + markDateLen + 2;

		longTimeLen = 2 + markTimeLen + 2 + markTimeLen + 2;
		medTimeLen = 2 + markTimeLen + 2;

		longDatetimeLen = longDateLen + dtspaceLen + longTimeLen;
		medDatetimeLen = medDateLen + dtspaceLen + medTimeLen;
	}

	/**
	 * 取得当前设置的日期之间的分隔符
	 * 
	 * @return 日期之间的分隔符
	 */
	public String getDateMark()
	{
		return markDate;
	}

	/**
	 * 取得当前设置的时间之间的分隔符号
	 * 
	 * @return 时间之间的分隔符号
	 */
	public String getTimeMark()
	{
		return markTime;
	}

	/**
	 * 设置日期和时间之间的分割符号
	 * 
	 * @param vDtSpace
	 *            分割符号字符串
	 */
	public String getDtSpace()
	{
		return dtspace;
	}

	/**
	 * 取得一个日期时间字符串的日期部分
	 * 
	 * @param dtstr
	 *            日期时间字符串
	 * @return 日期部分的字符串
	 */
	public String getDatePart(String dtstr)
	{
		return StrUtil.getLeftStr(dtstr, longDateLen);
	}

	/**
	 * 取得一个日期时间字符串的时间部分
	 * 
	 * @param dtstr
	 *            日期时间字符串
	 * @return 时间部分的字符串
	 */
	public String getTimePart(String dtstr)
	{
		return StrUtil.getRightStr(dtstr, longTimeLen);
	}

	/**
	 * 取得当前的时间的字符串形式，包括年月日 时分秒
	 * 
	 * @return 按照指定格式的当前时间的描述(YYYY-MM-DD hh:mm:dd)
	 */
	public String getNowStr()
	{
		Calendar c = Calendar.getInstance();
		return getDateTimeStr(c);
	}

	public DateTime getDateTime(String datetimeStr)
	{
		String dateStr = StrUtil.getLeftStr(datetimeStr, this.dtspace);
		String timeStr = StrUtil.getRightStr(datetimeStr, this.dtspace);

		OutInteger pos = new OutInteger(0);
		DateTime dt = new DateTime();

		dt.setYear(StrUtil.getIntToken(dateStr, markDate, pos));
		dt.setMonth(StrUtil.getIntToken(dateStr, markDate, pos));
		dt.setDay(StrUtil.getIntToken(dateStr, markDate, pos));

		pos.setValue(0);
		dt.setHour(StrUtil.getIntToken(timeStr, markTime, pos));
		dt.setMinute(StrUtil.getIntToken(timeStr, markTime, pos));
		dt.setSecond(StrUtil.getIntToken(timeStr, markTime, pos));

		return dt;
	}

	public String getDateTimeStr(DateTime dt)
	{
		StringBuffer str = new StringBuffer();
		str.append(toDateStr(dt.getYear(), dt.getMonth(), dt.getDay()));
		str.append(dtspace);
		str.append(toTimeStr(dt.getHour(), dt.getMinute(), dt.getSecond()));
		return str.toString();
	}

	public static DateTime getDateTime()
	{
		Calendar c = Calendar.getInstance();
		return toDateTime(c);
	}

	public static DateTime toDateTime(long timeMillis)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMillis);
		return toDateTime(c);
	}

	public static DateTime toDateTime(Calendar c)
	{
		return new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}

	public static int getWeekDay(long time)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取得一个时间的中文格式
	 * 
	 * @param c
	 *            时间日历
	 * @param dateSpace
	 *            日期各个部分之间的分隔符号
	 * @param timeSpace
	 *            时间各个部分之间的分隔符号
	 * @return 按照指定格式的当前时间的描述(YYYY-MM-DD hh:mm:dd)
	 */
	public String getDateTimeStr(Calendar c)
	{
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		StringBuffer str = new StringBuffer();
		str.append(toDateStr(year, month, day));
		str.append(dtspace);
		str.append(toTimeStr(hour, minute, second));
		return str.toString();
	}

	/**
	 * 将形如:YYYY-MM-DD hh:mm:ss的日期时间转化为中等长度的格式:YY-MM-DD hh:mm
	 * 
	 * @param datetime
	 *            长日期时间格式字符串
	 * @return 中等长度格式的日期
	 */
	public String toMedDateTime(String datetime)
	{
		if (datetime == null)
		{
			return null;
		}
		int length = datetime.length();
		if (length <= medDatetimeLen)
		{
			return datetime;
		} else if (length == longDatetimeLen)
		{
			return datetime.substring(2, length - (2 + markTimeLen));
		} else
		{
			throw new IllegalArgumentException("无法识别的日期字符串: " + datetime);
		}
	}

	/**
	 * 按照缺省格式取得一个时间的中文格式
	 * 
	 * @param timeMills
	 *            以毫秒表示的日期时间
	 * @return 按照指定格式的当前时间的描述(YYYY-MM-DD hh:mm:dd)
	 */
	public String getDateTimeStr(long timeMills)
	{
		java.util.Date d = new java.util.Date(timeMills);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getDateTimeStr(c);
	}

	/**
	 * 获得一天的毫秒数
	 * 
	 * @Description:
	 * @param @return
	 * @return long
	 * @throws
	 * @author 要离
	 * @date 2017-4-26
	 */
	public static long getOneDayTimeMills()
	{
		return 24 * 60 * 60 * 1000L;
	}

	/**
	 * 取得当前日期的4位年数字符串
	 */
	public String getYearStr()
	{
		Calendar c = Calendar.getInstance();
		return Integer.toString(c.get(Calendar.YEAR));
	}

	/**
	 * 取得短的年份表示YY
	 */
	public String getShortYearStr()
	{
		return getYearStr().substring(2, 4);
	}

	/**
	 * 将一个长的日期时间(YYYY-MM-DD hh:mm:ss)转换成一个中等日期时间(YY-MM-DD hh:mm)
	 * 
	 * @param longDateTime
	 *            长日期时间字符串
	 * @return 中日期时间字符串
	 */
	public String longDttoMedDt(String longDateTime)
	{
		return longDateTime.substring(2, medDatetimeLen + 2);
	}

	/**
	 * 取得当前日期的2位月数字符串
	 */
	public String getMonthStr()
	{
		Calendar rightNow = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH) + 1;
		return (month > 9 && month < 13) ? "" + month : "0" + month;
	}

	/**
	 * 取得当前日期的2位日字符串
	 */
	public String getDayStr()
	{
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		return day < 10 ? "0" + day : "" + day;
	}

	/**
	 * 取得当前日期的完整符串，格式YYYYMMDD
	 */
	public String getDateStr()
	{
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return toDateStr(year, month, day);
	}

	public String getDateStr(DateTime date)
	{
		return toDateStr(date.getYear(), date.getMonth(), date.getDay());
	}

	/**
	 * 取得一个日期的年份
	 * 
	 * @param date
	 *            符合日期格式的日期
	 * @return 年份部分
	 */
	public int getYearOf(String date)
	{
		return Integer.parseInt(date.substring(0, 4));
	}

	/**
	 * 取得一个日期的月份
	 * 
	 * @param date
	 *            符合日期格式的日期
	 * @return 月份部分
	 */
	public int getMonthOf(String date)
	{
		return Integer.parseInt(date.substring(4 + markDateLen, 6 + markDateLen));
	}

	/**
	 * 取得一个日期的日部分
	 * 
	 * @param date
	 *            符合日期格式的日期
	 * @return 日部分
	 */
	public int getDayOf(String date)
	{
		return Integer.parseInt(date.substring(4 + 2 + markDateLen * 2));
	}

	/**
	 * 取得在两个日期之间的年数
	 * 
	 * @param startDate
	 *            8位开始日期
	 * @param endDate
	 *            8位结束日期
	 * @return 总的年数，包括开始年份和结束年份
	 */
	public int getYearCountBetween(String startDate, String endDate)
	{
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		return endYear - startYear + 1;
	}

	/**
	 * 取得在两个日期之间的月数
	 * 
	 * @param startDate
	 *            8位开始日期
	 * @param endDate
	 *            8位结束日期
	 * @return 总的月数，包括开始月份和结束月份
	 */
	public int getMonthCountBetween(String startDate, String endDate)
	{
		int startYear = getYearOf(startDate);
		int endYear = getYearOf(endDate);
		int startMonth = getMonthOf(startDate);
		int endMonth = getMonthOf(endDate);

		return (12 - startMonth + 1) + (endYear - startYear - 1) * 12 + endMonth;
	}

	/**
	 * 取得在两个日期之间的天数
	 * 
	 * @param startDate
	 *            8位开始日期
	 * @param endDate
	 *            8位结束日期
	 * @return 总的天数，包括开始和结束的日,如果开始日期和结束日期是同一天,那么就只有一天
	 */
	public int getDayCount(String startDate, String endDate)
	{
		int startYear = getYearOf(startDate);
		int endYear = getYearOf(endDate);
		int startMonth = getMonthOf(startDate);
		int endMonth = getMonthOf(endDate);
		int startDay = getDayOf(startDate);
		int endDay = getDayOf(endDate);

		int days = 0;

		if (startYear < endYear)
		{
			for (int year = startYear + 1; year <= endYear - 1; year++)
			{
				days += getDaysOfYear(year);
			}
			for (int month = startMonth + 1; month <= 12; month++)
			{
				days += getDaysOfMonth(startYear, month);
			}
			for (int month = 1; month <= endMonth - 1; month++)
			{
				days += getDaysOfMonth(endYear, month);
			}
			days += getDaysOfMonth(startYear, startMonth) - startDay + 1;
			days += endDay;
		} else if (startMonth < endMonth)
		{
			for (int month = startMonth + 1; month < endMonth; month++)
			{
				days += getDaysOfMonth(startYear, month);
			}
			days += getDaysOfMonth(startYear, startMonth) - startDay + 1;
			days += endDay;
		} else
		{
			days = endDay - startDay + 1;
		}
		return days;
	}

	/**
	 * 判断一个年份是否闰年
	 * 
	 * @param year
	 *            年份
	 * @return true - 是闰年，false - 不是闰年
	 */
	public static boolean isLeapYear(int year)
	{
		if (year % 400 == 0) {
			return true;
		}
		if (year % 100 == 0) {
			return false;
		}
        return year % 4 == 0;
    }

	/**
	 * 计算一年有多少天
	 * 
	 * @param year
	 *            年份
	 * @return 这一年的天数
	 */
	public static int getDaysOfYear(int year)
	{
		return isLeapYear(year) ? 366 : 365;
	}

	/**
	 * 计算一个月有多少天
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份, 1 -- 12
	 * @return 这个月的天数
	 */
	public static int getDaysOfMonth(int year, int month)
	{
		if (isLeapYear(year))
		{
			return DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
		} else
		{
			return DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
		}
	}

	/**
	 * 取得在两个日期之间的年数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 年数
	 */
	public int getYearCount(DateTime start, DateTime end)
	{
		int startYear = start.getYear();
		int endYear = end.getYear();
		return endYear - startYear;
	}

	/**
	 * 取得两个日期之间的月份数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 月份数
	 */
	public int getMonthCount(DateTime start, DateTime end)
	{
		int result = getYearCount(start, end) * 12;
		return result + end.getMonth() - start.getMonth();
	}

	/**
	 * 取得在两个日期之间的天数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 月数
	 */
	public int getDayCount(DateTime start, DateTime end)
	{
		int result = 0;
		for (int year = start.getYear(); year < end.getYear(); year++)
		{
			result += getDaysOfYear(year);
		}

		for (int month = 1; month < start.getMonth(); month++)
		{
			result -= getDaysOfMonth(start.getYear(), month);
		}
		result -= start.getDay();

		for (int month = 1; month < end.getMonth(); month++)
		{
			result += getDaysOfMonth(end.getYear(), month);
		}
		result += end.getDay();

		return result;
	}

	/**
	 * 取得两个日期时间的小时数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 小时数
	 */
	public int getHourCount(DateTime start, DateTime end)
	{
		int result = getDayCount(start, end) * 24;
		return result + end.getHour() - start.getHour();
	}

	public DateTime[] getArray(DateTime start, DateTime end, int segType)
	{
		return null;
	}

	/**
	 * 取出两个日期之间的所有日期数据数组，包括开始日期和结束日期
	 * 
	 * @param startDate
	 *            符合日期格式的开始日期
	 * @param endDate
	 *            符合日期格式的结束日期
	 * @param segType
	 *            分隔的类型
	 * @return 日期字符串数组。当分隔方式无效时返回null.
	 */

	public String[] getDateArray(String startDate, String endDate, int segType)
	{
		int startYear = 0, startMonth = 0, startDay = 0;

		String[] arr = null;
		int count = 0;

		startYear = getYearOf(startDate);

		if (segType == DATE_SEG_YEAR)
		{
			count = getYearCountBetween(startDate, endDate);
			arr = new String[count];
			for (int i = 0; i < count; i++)
			{
				arr[i] = "" + (startYear + i);
			}
			return arr;
		}

		startMonth = getMonthOf(startDate);

		if (segType == DATE_SEG_MONTH)
		{
			count = getMonthCountBetween(startDate, endDate);
			arr = new String[count];
			int year = startYear;
			int month = startMonth;
			for (int i = 0; i < count; i++)
			{
				arr[i] = fixToLength(year, 4) + markDate + fixToLength(month, 2);
				month++;
				if (month == 13)
				{
					month = 1;
					year++;
				}
			}
			return arr;
		}

		startDay = getDayOf(startDate);

		if (segType == DATE_SEG_DAY)
		{
			count = getDayCount(startDate, endDate);
			arr = new String[count];
			int year = startYear;
			int month = startMonth;
			int day = startDay;
			for (int i = 0; i < count; i++)
			{
				arr[i] = fixToLength(year, 4) + markDate + fixToLength(month, 2) + markDate + fixToLength(day, 2);
				day++;
				if (day > getDaysOfMonth(year, month))
				{
					day = 1;
					month++;
					if (month > 12)
					{
						month = 1;
						year++;
					}
				}
			}
			return arr;
		}

		return null;
	}

	/**
	 * 取得当前时间字符串，用默认的格式进行格式化 结果中，每个域均为2个字符，即类似于：19:09:09
	 * 
	 * @return 当前时间字符串
	 */
	public String getTimeStr()
	{
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		StringBuffer result = new StringBuffer();
		if (hour < 10) {
			result.append("0");
		}
		result.append(hour).append(markTime);
		if (minute < 10) {
			result.append("0");
		}
		result.append(minute).append(markTime);
		if (second < 10) {
			result.append("0");
		}
		result.append(second);
		return result.toString();
	}

	/**
	 * 对一个日期部分补足长度
	 * 
	 * @param val
	 *            日期部分
	 * @param len
	 *            希望的长度
	 * @return 固定长度的字符串
	 */
	private String fixToLength(int val, int length)
	{
		String valStr = "" + val;

		if (valStr.length() >= length) {
			return valStr;
		}

		StringBuffer buff = new StringBuffer();

		for (int i = valStr.length(); i < length; i++)
		{
			buff.append("0");
		}
		return buff.append(valStr).toString();
	}

	/**
	 * 计算指定日期前或者后几天的日期
	 * 
	 * @param date
	 *            指定日期
	 * @param days
	 *            天数，> 0 表示几天后； < 0 表示几天前
	 * @return 符合格式规定的日期字符串
	 */
	public final String getDateAt(String date, int days)
	{
		if (days > 0)
		{
			return getDateAfter(date, days);
		} else
		{
			return getDateBefore(date, -days);
		}
	}

	/**
	 * 计算某一天之后的几天后的日期字符串
	 * 
	 * @param dateStart
	 *            符合日期格式开始日期
	 * @param days
	 *            以后的天数
	 * @return 以后的日期字符串，符合日期格式规定
	 */
	public String getDateAfter(String date, int days)
	{
		int year = getYearOf(date);
		int month = getMonthOf(date);
		int day = getDayOf(date);

		// 计算是否为闰年
		boolean asLeap = isLeapYear(year);
		day = day + days;

		while (isValidDate(year, month, day) == false)
		{
			if (asLeap)
			{
				day -= DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
			} else
			{
				day -= DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
			}

			month++;
			if (month > 12)
			{
				year++;
				month = 1;
				asLeap = isLeapYear(year);
			}
		}
		return toDateStr(year, month, day);
	}

	/**
	 * 计算指定日期之前的几天的日期字符串
	 * 
	 * @param dateStart
	 *            符合日期格式的开始日期
	 * @param days
	 *            以前的天数
	 * @return 以前日期字符串，符合日期格式规定
	 */
	public String getDateBefore(String date, int days)
	{
		int year = getYearOf(date);
		int month = getMonthOf(date);
		int day = getDayOf(date);

		// 计算是否为闰年
		boolean asLeap = isLeapYear(year);
		day = day - days;

		while (isValidDate(year, month, day) == false)
		{
			month--;
			if (month == 0)
			{
				year--;
				month = 1;
				asLeap = isLeapYear(year);
			}

			if (asLeap)
			{
				day += DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
			} else
			{
				day += DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
			}
		}
		return toDateStr(year, month, day);
	}

	/**
	 * 检查一个日期时间字符串是否有效
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串
	 * @return true 有效；false 无效
	 */
	public boolean isValidDateTime(String dateTimeStr)
	{
		try
		{
			String dateStr = StrUtil.getLeftStr(dateTimeStr, dtspace);
			if (isValidDate(dateStr) == false) {
				return false;
			}

			String timeStr = StrUtil.getRightStr(dateTimeStr, dtspace);
            return isValidTime(timeStr) != false;
        } catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 取得一个时间字符串中的小时数
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @return 小时
	 */
	public int getHourOf(String timeStr)
	{
		return Integer.parseInt(timeStr.substring(0, 2));
	}

	/**
	 * 取得一个日期时间字符串的小时数
	 * 
	 * @param dateStr
	 *            日期时间格式的字符串
	 * @param asLong
	 *            是否完整的日期时间
	 * @return
	 */
	public int getHourOf(String dateStr, boolean asFull)
	{
		if (asFull) {
			dateStr = getTimePart(dateStr);
		}
		return Integer.parseInt(StrUtil.getLeftStr(dateStr, markTime));
	}

	/**
	 * 取得一个时间字符串中的分钟数
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @return 分钟数
	 */
	public int getMinuteOf(String timeStr)
	{
		return Integer.parseInt(timeStr.substring(2 + markTimeLen, 2 + markTimeLen + 2));
	}

	/**
	 * 取得一个时间字符串中的秒数
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @return 秒数
	 */
	public int getSecondOf(String timeStr)
	{
		return Integer.parseInt(timeStr.substring(2 + 2 + markTimeLen * 2));
	}

	/**
	 * 检查一个时间字符串是否有效
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @return true 有效；false 无效
	 */
	public boolean isValidTime(String timeStr)
	{
		return isValidTime(timeStr, true);
	}

	/**
	 * 检查一个时间的各个部分是否有效
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return true 有效；false 无效
	 */
	public boolean isValidTime(int hour, int minute, int second)
	{
		if (hour < 0 || hour > 23) {
			return false;
		}
		if (minute < 0 || minute > 59) {
			return false;
		}
        return second >= 0 && second <= 59;
    }

	/**
	 * 检查一个日期字符串是否有效
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return true 有效；false 无效
	 */
	public boolean isValidDate(String dateStr)
	{
		if (dateStr == null || dateStr.length() != getDateStrLen())
		{
			return false;
		}
		if (dateStr.indexOf(markDate) != 4) {
			return false;
		}
		if (dateStr.indexOf(markDate, 4 + markDate.length()) != 6 + markDate.length())
		{
			return false;
		}
		try
		{
			int year = getYearOf(dateStr);
			int month = getMonthOf(dateStr);
			int day = getDayOf(dateStr);
			return isValidDate(year, month, day);
		} catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 * 检查构成日期的各个部分是否有效
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return true 有效；false 无效
	 */
	public static boolean isValidDate(int year, int month, int day)
	{
		if (day <= 0) {
			return false;
		}

		boolean asLeap = isLeapYear(year);
		if (asLeap)
		{
			return day <= DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
		} else
		{
			return day <= DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
		}
	}

	/**
	 * 检查一个事件字符串的内容是否是合格的时间描述
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @param asLong
	 *            是否完整的时间：false - hh:mm; true - hh:mm:ss
	 * @return true 数据有效；false 数据无效
	 */
	public boolean isValidTime(String timeStr, boolean asLong)
	{
		if (timeStr.length() != getTimeStrLen(asLong))
		{
			return false;
		}

		try
		{
			OutInteger pos = new OutInteger(0);
			int hour = StrUtil.getIntToken(timeStr, markTime, pos);
			int minute = StrUtil.getIntToken(timeStr, markTime, pos);
			int sec = asLong ? StrUtil.getIntToken(timeStr, markTime, pos) : 0;
			// System.out.println("time:----{" + hour + "," + minute + "," + sec
			// + "}");
			return isValidTime(hour, minute, sec);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 返回一个可以代表指定的时间的毫秒数
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串，符合格式规定
	 * @return 毫秒数
	 */
	public long getTimeMillis(String dateTimeStr)
	{
		return dtStrToDt(dateTimeStr).toTimeMillis();
	}

	/**
	 * 将一个完整格式的日期时间字符串转变为一个日期时间格式
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串，符合格式规定
	 * @return 对应日期时间对象
	 */
	public DateTime dtStrToDt(String dateTimeStr)
	{
		OutInteger pos = new OutInteger(0);
		int year = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, markDate, pos));
		int month = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, markDate, pos));
		int day = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, dtspace, pos));
		int hour = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, markTime, pos));
		int minute = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, markTime, pos));
		int second = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, markTime, pos));

		return new DateTime(year, month, day, hour, minute, second);
	}

	/**
	 * 从日期构造一个字符串
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 日期字符串
	 */
	public String toDateStr(int year, int month, int day)
	{
		StringBuffer strb = new StringBuffer();
		String yearStr = fixToLength(year, 4);
		String monthStr = fixToLength(month, 2);
		String dayStr = fixToLength(day, 2);
		strb.append(yearStr).append(markDate).append(monthStr).append(markDate).append(dayStr);
		return strb.toString();
	}

	/**
	 * 根据给定的日期、时间的各个部分，构造一个日期-时间字符串
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param sec
	 *            秒
	 * @return 日期-时间字符串
	 */
	public String toDateTimeStr(int year, int month, int day, int hour, int minute, int sec)
	{
		String dateStr = toDateStr(year, month, day);
		String timeStr = toTimeStr(hour, minute, sec);
		return dateStr + dtspace + timeStr;
	}

	/**
	 * 将一个日期字符串转换为日期
	 * 
	 * @param dateStr
	 *            符合日期格式规定的长日期字符串
	 * @return 日期,包含年,月,日
	 */
	public DateTime dateStrToDate(String dateStr)
	{
		if (isValidDate(dateStr) == false)
		{
			throw new RuntimeException("日期字符串格式错误: " + dateStr);
		}

		int year = Integer.parseInt(dateStr.substring(0, 4));
		int monthStart = 4 + markDate.length();
		int monthEnd = monthStart + 2;
		int month = Integer.parseInt(dateStr.substring(monthStart, monthEnd));
		int day = Integer.parseInt(dateStr.substring(dateStr.length() - 2));
		return new DateTime(year, month, day);
	}

	/**
	 * 将一个常日期格式的字符串转换成只有月日部分的字符串
	 * 
	 * @param dateStr
	 *            yyyy-mm-dd 格式的日期字符串
	 * @return 短日期格式：mm-dd
	 */
	public String toShortDate(String longDateStr)
	{
		if (longDateStr.length() != getDateStrLen())
		{
			throw new IllegalArgumentException("日期格式错误: " + longDateStr);
		}
		return longDateStr.substring(4 + markDateLen);
	}

	/**
	 * 将一个完整的时间字符产转变为短格式，只包含小时，分钟
	 * 
	 * @param longTimeStr
	 *            原来的时间字符串，格式：hh:mm:ss
	 * @return 短时间格式 hh:mm
	 */
	public String toShortTime(String longTimeStr)
	{
		if (longTimeStr.length() != getTimeStrLen())
		{
			throw new IllegalArgumentException("时间格式错误: " + longTimeStr);
		}
		return longTimeStr.substring(0, 4 + markTimeLen);
	}

	/**
	 * 取得当前日期格式设定的日期字符串的长度
	 * 
	 * @return 日期字符串长度
	 */
	public int getDateStrLen()
	{
		return 4 + markDateLen + 2 + markDateLen + 2;
	}

	/**
	 * 取得当前日期格式设定的日期字符串的长度
	 * 
	 * @return 日期字符串的长度
	 */
	public int getTimeStrLen()
	{
		return 2 + markTimeLen + 2 + markTimeLen + 2;
	}

	/**
	 * 取得当前日期格式中时间部分的长度(包括时分秒分隔符号)
	 * 
	 * @param longTime
	 *            是否为长时间格式,长时间包括时分秒三部分,而短时间则只有时分或者分秒
	 * @return 时间格式的长度
	 */
	public int getTimeStrLen(boolean longTime)
	{
		return longTime ? markTimeLen * 2 + 2 * 3 : markTimeLen + 2 * 2;
	}

	/**
	 * 将指定的小时、分钟、秒合成一个时间字符串
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param sec
	 *            秒
	 * @return 时间字符串
	 */
	public String toTimeStr(int hour, int minute, int sec)
	{
		StringBuffer sb = new StringBuffer(fixToLength(hour, 2));
		sb.append(markTime).append(fixToLength(minute, 2));
		sb.append(markTime).append(fixToLength(sec, 2));
		return sb.toString();
	}

	/**
	 * 将一个日期还原成标准的地理日历
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 标准的地理日历
	 */
	public GregorianCalendar toCalendar(String dateStr)
	{
		OutInteger pos = new OutInteger(0);
		String dateMark = this.markDate;
		int year = StrUtil.getIntToken(dateStr, dateMark, pos);
		int month = StrUtil.getIntToken(dateStr, dateMark, pos);
		int day = StrUtil.getIntToken(dateStr, dateMark, pos);
		return new GregorianCalendar(year, month - 1, day);
	}

	/**
	 * 将一个日期还原成标准的地理日历
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 标准的地理日历
	 */
	public static GregorianCalendar toCalendar(DateTime dt)
	{
		return new GregorianCalendar(dt.getYear(), dt.getMonth() - 1, dt.getDay());
	}

	/**
	 * 将当前月日时分秒时间转化为一个10位数字的整数,例如"2004-12-25 15:06:18"=>1225150618
	 * 
	 * @return 一个表示当前时间的整数
	 */
	public static int getTime()
	{
		Calendar c = Calendar.getInstance();
		int num = c.get(Calendar.YEAR);
		num = num * 100 + c.get(Calendar.MONTH) + 1;
		num = num * 100 + c.get(Calendar.DAY_OF_MONTH);
		num = num * 100 + c.get(Calendar.HOUR_OF_DAY);
		num = num * 100 + c.get(Calendar.MINUTE);
		num = num * 100 + c.get(Calendar.SECOND);
		return num;
	}

	/**
	 * 将一个日历时间转为在当前一周中的分钟数,例如星期二的下午15:30=2*24*60 + 15 * 60 + 30
	 * 
	 * @param time
	 *            一个日历时间
	 * @return 时间转为在当前一周中的分钟数
	 */
	public static int calendarToWeekMinute(Calendar time)
	{
		// 计算现在的时间特性是星期几了
		int weekDay = time.get(Calendar.DAY_OF_WEEK) - 1;
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int minute = time.get(Calendar.MINUTE);
		// System.out.println("week day=" + weekDay + ", hour=" + hour +
		// ", minute=" + minute
		// + ",week minute=" + (weekDay * 24 * 60 + hour * 60 + minute));
		// 计算当前是什么时间了(以分钟的形式表示时间)
		return weekDay * 24 * 60 + hour * 60 + minute;
	}

	/**
	 * 将一个不完整的日期时间字符串转换为完整的日期时间字符串
	 * <p>
	 * 本函数仅用于对日期类型数据的查询条件的整理,并不保证返回的日期时间字符串是一个有效的时间.
	 * 它只是简单返回一个符合基本时间格式的日期时间字符串,不对数据有孝心进行校验.
	 * </p>
	 * <p>
	 * 当字符串为NULL,或者0长度字符串时,本函数将返回null.
	 * </p>
	 * 
	 * @param dateStr
	 *            一个日期字符串
	 * @param asMax
	 *            时间部分是最大值,还是最小值
	 * @return 对应的日期字符串
	 */
	public String fullDateTime(String dateStr, boolean asMax)
	{
		if (dateStr == null)
		{
			return null;
		}

		dateStr = dateStr.trim();
		if (dateStr.length() == 0)
		{
			return null;
		}

		DateTime datetime = asMax ? DateTime.MAX_DATETIME : DateTime.MIN_DATETIME;
		char[] dtchars = this.getDateTimeStr(datetime).toCharArray();
		int copylen = Math.min(dtchars.length, dateStr.length());
		System.arraycopy(dateStr.toCharArray(), 0, dtchars, 0, copylen);

		return new String(dtchars);
	}

	public static void main(String[] args)
	{
		String str = "431321198801210039";
		if (str == null)
		{
			return;
		}
		int length = str.length();
		if (length == 0)
		{
			return;
		}
		StringBuffer sb = new StringBuffer();
		if (length < 2)
		{
			sb.append("*");
		} else if (length < 3)
		{
			sb.append(str, 0, 1);
			sb.append("*");
		} else if (length <= 5)
		{
			sb.append(str, 0, 1);
			for (int i = 0; i < length - 2; i++)
			{
				sb.append("*");
			}
			sb.append(str, length - 1, length);
		} else if (length <= 10)
		{
			sb.append(str, 0, 2);
			for (int i = 0; i < length - 4; i++)
			{
				sb.append("*");
			}
			sb.append(str, length - 2, length);
		} else if (length <= 20)
		{
			sb.append(str, 0, 4);
			for (int i = 0; i < length - 8; i++)
			{
				sb.append("*");
			}
			sb.append(str, length - 4, length);
		} else
		{
			sb.append(str);
		}
		System.out.println(sb.toString());
	}

}
