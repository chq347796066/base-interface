package com.spring.common.util.date;

import com.spring.common.util.string.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:时间工具类
*/
public class DateTime {

	/** 日期时间的最小值: 0000-00-00 00:00:00 */
	public static final DateTime MIN_DATETIME = new DateTime(0000, 00, 00);

	/** 日期时间的最大值: 9999-12-31 23:59:59 */
	public static final DateTime MAX_DATETIME = new DateTime(9999, 12, 31, 23, 59, 59);

	private int mYear = 1900;
	private int mMonth = 1;
	private int mDay = 1;
	private int mHour = 0;
	private int mMinute = 0;
	private int mSecond = 0;

	public DateTime()
	{
	}

	public DateTime(int year, int month, int day)
	{
		setYear(year);
		setMonth(month);
		setDay(day);
	}

//	public static void main(String[] args)
//	{
//		DateTime dt = DateHelper.getDateTime();
//		System.out.println(dt.getMonthAt(-1).getDateStr());
//		System.out.println(dt.getMonthAt(1).getDateStr());
//	}

	public DateTime(int year, int month, int day, int hour, int minute, int second)
	{
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}

	public void setYear(int year)
	{
		mYear = year;
	}

	public int getYear()
	{
		return mYear;
	}

	public void setMonth(int month)
	{
		mMonth = month;
	}

	public int getMonth()
	{
		return mMonth;
	}

	public String getMonthStr()
	{
		return (mMonth < 10 ? "0" + mMonth : Integer.toString(mMonth));
	}

	public void setDay(int day)
	{
		mDay = day;
	}

	public int getDay()
	{
		return mDay;
	}

	public String getDayStr()
	{
		return (mDay < 10 ? "0" + mDay : Integer.toString(mDay));
	}

	public int getWeek()
	{
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth - 1, mDay);
		int w = c.get(Calendar.DAY_OF_WEEK);
		if (w == 0) {
			w = 7;
		}
		return w;
	}

	public void setHour(int hour)
	{
		mHour = hour;
	}

	public int getHour()
	{
		return mHour;
	}

	public void setMinute(int minute)
	{
		mMinute = minute;
	}

	public int getMinute()
	{
		return mMinute;
	}

	public void setSecond(int sec)
	{
		mSecond = sec;
	}

	public int getSecond()
	{
		return mSecond;
	}

	/**
	 * 将当前日期时间转变为系统毫秒格式
	 * 
	 * @return 系统毫秒格式的时间
	 */
	public long toTimeMillis()
	{
		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth - 1, mDay, mHour, mMinute, mSecond);
		return c.getTime().getTime();
	}

	/**
	 * 计算指定日期前或者后几天的日期
	 * 
	 * @param days
	 *            天数，> 0 表示几天后； < 0 表示几天前
	 * @return 日期
	 */
	public DateTime getDateAt(int days)
	{
		return days > 0 ? getDateAfter(days) : getDateBefore(-days);
	}

	/**
	 * 计算指定日期前或者后几月的日期
	 * 
	 * @param months
	 *            月数，> 0 表示几月后； < 0 表示几月前
	 * @return 日期
	 */
	public DateTime getMonthAt(int months)
	{
		GregorianCalendar c = DateHelper.toCalendar(this);
		c.add(Calendar.MONTH, months);
		return DateHelper.toDateTime(c.getTimeInMillis());
	}

	/**
	 * 计算指定日期前或者后几年的日期
	 * 
	 * @param months
	 *            年数，> 0 表示几年后； < 0 表示几年前
	 * @return 日期
	 */
	public DateTime getYearAt(int years)
	{
		return getMonthAt(years * 12);
	}

	/**
	 * 计算指定日期之前的几天的日期
	 * 
	 * @param days
	 *            以前的天数
	 * @return 以前日期
	 */
	public DateTime getDateBefore(int days)
	{
		int year = mYear;
		int month = mMonth;
		int day = mDay;

		// 计算是否为闰年
		day = day - days;
		while (DateUtil.isValidDate(year, month, day) == false)
		{
			month--;
			if (month == 0)
			{
				year--;
				month = 12;
			}

			day += DateUtil.getDaysOfMonth(year, month);
		}
		return new DateTime(year, month, day, 0, 0, 0);
	}

	/**
	 * 计算某一天之后的几天后的日期
	 * 
	 * @param days
	 *            以后的天数
	 * @return 以后的日期
	 */
	public DateTime getDateAfter(int days)
	{
		int year = mYear;
		int month = mMonth;
		int day = mDay;

		// 计算是否为闰年
		day = day + days;
		while (DateUtil.isValidDate(year, month, day) == false)
		{
			day -= DateUtil.getDaysOfMonth(year, month);
			month++;
			if (month > 12)
			{
				year++;
				month = 1;
			}
		}
		return new DateTime(year, month, day, 0, 0, 0);
	}

	/**
	 * 取得当前时间
	 * 
	 * @return 当前时间
	 */
	public static DateTime getNow()
	{
		Calendar c = Calendar.getInstance();
		return toDate(c);
	}

	/**
	 * 将一个长整数毫秒表示的时间转换为时间
	 * 
	 * @param time
	 *            毫秒时间
	 * @return 时间
	 */
	public static DateTime toDate(long time)
	{
		Date d = new Date(time);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(d);
		return toDate(c);
	}

	/**
	 * 将一个日历时间转换为时间
	 * 
	 * @param c
	 *            日历
	 * @return 时间
	 */
	public static DateTime toDate(Calendar c)
	{
		DateTime date = new DateTime();
		date.setYear(c.get(Calendar.YEAR));
		date.setMonth(c.get(Calendar.MONTH) + 1);
		date.setDay(c.get(Calendar.DAY_OF_MONTH));
		date.setHour(c.get(Calendar.HOUR_OF_DAY));
		date.setMinute(c.get(Calendar.MINUTE));
		date.setSecond(c.get(Calendar.SECOND));
		return date;
	}

	/**
	 * 和另外一个日期比较,判断大小
	 * 
	 * @param other
	 *            另一个日期时间
	 * @return 1->本日期比另一个日期大; 0->两个日期一样大; -1->比另一个时间小
	 */
	public int compareTo(DateTime other)
	{
		if (mYear > other.mYear)
		{
			return 1;
		}
		if (mYear < other.mYear)
		{
			return -1;
		}
		if (mMonth > other.mMonth)
		{
			return 1;
		}
		if (mMonth < other.mMonth)
		{
			return -1;
		}
		if (mDay > other.mDay)
		{
			return 1;
		}
		if (mDay < other.mDay)
		{
			return -1;
		}
		if (mHour > other.mHour)
		{
			return 1;
		}
		if (mHour < other.mHour)
		{
			return -1;
		}
		if (mMinute > other.mMinute)
		{
			return 1;
		}
		if (mMinute < other.mMinute)
		{
			return -1;
		}
		if (mSecond > other.mSecond)
		{
			return 1;
		}
		if (mSecond < other.mSecond)
		{
			return -1;
		}
		return 0;
	}

	/**
	 * 取得日期部分的字符串表示，格式：YYYYMMDD
	 * 
	 * @return 日期部分的字符串
	 */
	public String getDateStr()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.fixToLength(mYear, 4)).append(DateUtil.fixToLength(mMonth, 2)).append(DateUtil.fixToLength(mDay, 2));
		return sb.toString();
	}

	/**
	 * 取得日期时间的字符串表示，格式：YYYYMMDDhhmmss
	 * 
	 * @return 日期时间字符串
	 */
	public String getDateTimeStr()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.fixToLength(mYear, 4)).append(DateUtil.fixToLength(mMonth, 2)).append(DateUtil.fixToLength(mDay, 2));
		sb.append(DateUtil.fixToLength(mHour, 2)).append(DateUtil.fixToLength(mMinute, 2)).append(DateUtil.fixToLength(mSecond, 2));
		return sb.toString();
	}
	
	/**
	 * 取得日期时间的字符串表示，按照中国人习惯格式
	 * 
	 * @return 日期时间字符串
	 */
    public String getDateTimeChinaStr()
    {
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.fixToLength(mYear, 4)).append("-").append(DateUtil.fixToLength(mMonth, 2)).append("-").append(DateUtil.fixToLength(mDay, 2));
		sb.append(" ");
		sb.append(DateUtil.fixToLength(mHour, 2)).append("-").append(DateUtil.fixToLength(mMinute, 2)).append("-").append(DateUtil.fixToLength(mSecond, 2));
		return sb.toString();
    }

	/**
	 * 取得时间部分的字符串表示,格式：hhmmss
	 * 
	 * @return 日期字符串
	 */
	public String getTimeStr()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.fixToLength(mHour, 2)).append(DateUtil.fixToLength(mMinute, 2)).append(DateUtil.fixToLength(mSecond, 2));
		return sb.toString();
	}

	/**
	 * 将一个日期字符串解释成为一个日期
	 * <p>
	 * 日期字符串可以是8位日期YYYYMMDD； 也可以是6位时间hhmmss； 也可以是15位日期时间，中间用空格分割YYYYMMDD hhmmss;
	 * </p>
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 日期
	 */
	public static DateTime parseDate(String dateStr)
	{
		String datePart = null, timePart = null;
		int length = dateStr.length();
		if (length == 8)
		{
			datePart = dateStr;
		} else if (length == 6)
		{
			timePart = dateStr;
		} else if (length == 15)
		{
			datePart = StrUtil.getLeftStr(dateStr, " ");
			timePart = StrUtil.getRightStr(dateStr, " ");
		}
		if (datePart == null && timePart == null)
		{
			throw new IllegalArgumentException("无效的日期、时间描述:" + dateStr);
		}

		DateTime date = new DateTime();
		if (datePart != null)
		{
			date.setYear(Integer.parseInt(datePart.substring(0, 4)));
			date.setMonth(Integer.parseInt(datePart.substring(4, 6)));
			date.setDay(Integer.parseInt(datePart.substring(6)));
		}
		if (timePart != null)
		{
			date.setHour(Integer.parseInt(timePart.substring(0, 2)));
			date.setMinute(Integer.parseInt(timePart.substring(2, 4)));
			date.setSecond(Integer.parseInt(timePart.substring(4)));
		}
		return date;
	}

	public void copyFrom(DateTime source)
	{
		this.mYear = source.mYear;
		this.mMonth = source.mMonth;
		this.mDay = source.mDay;
		this.mHour = source.mHour;
		this.mMinute = source.mMinute;
		this.mSecond = source.mSecond;
	}

	public DateTime makeCopy()
	{
		DateTime copy = new DateTime();
		copy.copyFrom(this);
		return copy;
	}

	/**
	 * @author 熊锋
	 * @param expire
	 * @date 2020/7/8 9:39
	 * @description: 设置微信二维码过期时间
	 * @return java.lang.String
	 * @throws Exception
	 */
	public static String getOrderExpireTime(Long expire){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		Date now=new Date();
		Date afterDate=new Date(now.getTime()+expire);
		return simpleDateFormat.format(afterDate);
	}

	/**
	 * @author 熊锋
	 * @date 2020/7/8 9:39
	 * @description: 生成订单号
	 * @return java.lang.String
	 * @throws Exception
	 */
	public static String createOderNum(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		Date now=new Date();
		String time=String.valueOf(System.currentTimeMillis());
		return simpleDateFormat.format(now.getTime())+time;
	}

	/**
	 * @author 熊锋
	 * @date 2020/7/8 9:39
	 * @description: 将yyyyMMddHHmmss转成yyyy-MM-dd HH:mm:ss
	 * @return java.lang.String
	 * @throws Exception
	 */
	public static LocalDateTime strToDateLong(String strDate) throws ParseException {
		Date date = new Date();
		try {
			//先按照原格式转换为时间
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//再将时间转换为对应格式字符串
		String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime=LocalDateTime.parse(str,fmt);
		return localDateTime;
	}

}
