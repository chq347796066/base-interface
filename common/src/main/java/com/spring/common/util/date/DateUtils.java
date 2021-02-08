package com.spring.common.util.date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description:日期帮助类
 * @author  
 * @date 2017年11月7日上午9:07:26
 * @update
 */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final String DATE_FORMAT_FULL = "yyyyMMddHHmmss";

	public static String DEFAULT_FORMAT = "yyyyMMdd";

	/**
	 * 短日期格式
	 */
	public static final String DATE_FORMAT_YMDI = "yyyy/MM/dd";
	/**
	 * 时间格式不带分隔符
	 */
	public static final String DATE_FORMAT_FULL_II = "yyyyMMddHHmmss";
	/**
	 * 短日期格式
	 */
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/**
	 * 短日期格式HH:mm:ss
	 */
	public static final String DATE_FORMAT_HMS = "HH:mm:ss";
	/**
	 * 短日期格式不带分隔符
	 */
	public static final String DATE_FORMAT_YMD_II = "yyyyMMdd";
	public static final String DATE_FORMAT_DAY_YEAR_II = "yyyyMM";
	public static final String DATE_FORMAT_DAY_YEAR_II_UNIT = "yyyy年MM月";


	/**
	 * 获取系统时间
	 *
	 * @param
	 * @return String
	 */
	public static String getCurrentTime() {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_FULL);
		return format.format(new Date());
	}

	/**
	 * 获取系统时间
	 *
	 * @param style
	 * @return String
	 */
	public static String getCurrentTime(String style) {
		DateFormat format = new SimpleDateFormat(style);
		return format.format(new Date());
	}

	/**
	 * 日期格式字符串转换
	 *
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String dateToStr(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 字符串日期格式转换
	 *
	 * @param dateStr
	 * @param format
	 * @return String
	 */
	public static Date strToDate(String dateStr, String format) {
		try {
			if (!StringUtils.isEmpty(dateStr)) {
				return new SimpleDateFormat(format).parse(dateStr);
			} else {
				return null;
			}
		} catch (ParseException e) {
			logger.error("strToDate字符串日期格式转换异常" + e);
		}
		return new Date();
	}

	/**
	 * 日期相减
	 *
	 * @param begin
	 * @param end
	 * @return long
	 */
	public static long dateDifference(Date begin, Date end) {
		long to = end.getTime();
		long from = begin.getTime();
		return to - from;
	}

	/**
	 * 某个时间加上一天
	 *
	 * @param date
	 * @return String
	 */
	public static String addOneDay(String date) {
		String addDate = date;
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		try {
			Date preDate = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(preDate);
			calendar.add(Calendar.DATE, 1);
			addDate = format.format(calendar.getTime());
		} catch (ParseException e) {
			logger.error("addOneDay某个时间加上一天异常" + e);
			return addDate;
		}
		return addDate;
	}

	/**
	 * 某个时间加上n天
	 *
	 * @param date
	 * @return String
	 */
	public static String addDay(String date, int add) {
		String addDate = date;
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		try {
			Date preDate = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(preDate);
			calendar.add(Calendar.DATE, add);
			addDate = format.format(calendar.getTime());
		} catch (ParseException e) {
			logger.error("addOneDay某个时间加上一天异常" + e);
			return addDate;
		}
		return addDate;
	}


	public static String getFristDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		String startDate = format.format(calendar.getTime());
		return startDate;
	}

	/**
	 * 获取当月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		String startDate = format.format(calendar.getTime());
		return startDate;
	}

	/**
	 * 获取当月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return getLastDayOfMonth(sdf.parse(date));
	}

	/**
	 * 获取当月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String getFristDayOfNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		String startDate = format.format(calendar.getTime());
		return startDate;
	}

	/**
	 * 获取上个月的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String getlastDayOfBeforeMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		String startDate = format.format(calendar.getTime());
		return startDate;
	}

	/**
	 * 获取上个月的第一天
	 *
	 * @param date
	 * @return
	 */
	public static String getFristDayOfBeforeMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		String startDate = format.format(calendar.getTime());
		return startDate;
	}

	/**
	 * @description:日期比较
	 * @author
	 * @date
	 */
	public static int conparaCurrentMonth(String month) {
		int para = 0;
		Calendar calendar2 = Calendar.getInstance();
		try {
			calendar2.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(getFristDayOfMonth(new Date())));
		} catch (ParseException e) {
			logger.error("conparaCurrentMonth日期比较异常" + e);
		}
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(month));
		} catch (ParseException e) {
			logger.error("conparaCurrentMonth日期比较异常" + e);
		}
		para = calendar.compareTo(calendar2);
		return para;

	}

	/**
	 * @param @param  day
	 * @param @param  cycle
	 * @param @return
	 * @return String
	 * @throws
	 * @Description: 获取几个月后的最后一天
	 * @author
	 */
	public static String getLastDayAfterMonths(String day, int cycle) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isEmpty(day)) {
			calendar.setTime(new Date());
		} else {
			try {
				calendar.setTime(formatter.parse(day));
			} catch (ParseException e) {
				logger.error("addMonth月份添加异常" + e);
			}
		}
		calendar.add(Calendar.MONTH, cycle);
		return DateUtils.getLastDayOfMonth(calendar.getTime());
	}

	/**
	 * 获取几个月之后的日期
	 *
	 * @param beginDate
	 * @param cycle
	 * @return
	 */
	public static String getTimeAfterMonths(String beginDate, int cycle) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isEmpty(beginDate)) {
			calendar.setTime(new Date());
		} else {
			try {
				calendar.setTime(formatter.parse(beginDate));
			} catch (ParseException e) {
				logger.error("getTimeAfterMonths获取几个月之后的日期" + e);
			}
		}
		calendar.add(Calendar.MONTH, cycle);
		return formatter.format(calendar.getTime());
	}


	/**
	 * 功能: 判断是否是月末
	 *
	 * @param 日期
	 * @return true月末, false不是月末
	 */
	public static boolean isMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
        return calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能: 获取当月剩余天数
	 *
	 * @param 日期
	 * @return true月末, false不是月末
	 */
	public static int getleftDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DATE);
	}

	/**
	 * 功能: 获取当月剩余天数比率
	 *
	 * @param 日期
	 * @return true月末, false不是月末
	 */
	public static double getleftDayRate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		double rate = Double.valueOf((calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DATE)) + "")
				/ calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return rate;
	}

	/**
	 * 日期比较 0等 -1小于 1大于
	 *
	 * @param month
	 * @return
	 */
	public static int conparaTwoMonth(String month, String otherMonth) {
		String otherMonths = otherMonth.substring(0, 6);
		String months = month.substring(0, 6);
		String date = "yyyyMM";
		int para = 0;
		Calendar calendar2 = Calendar.getInstance();
		try {
			calendar2.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(otherMonths));
		} catch (ParseException e) {
			logger.error("conparaTwoMonth日期比较" + e);
		}
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(months));
		} catch (ParseException e) {
			logger.error("conparaTwoMonth日期比较" + e);

		}
		para = calendar.compareTo(calendar2);
		return para;

	}

	/**
	 * 两个时间(yyyyMMddHHmmss格式)比较  0等 -1小于 1大于
	 *
	 * @param begain
	 * @param end
	 * @return
	 */
	public static int compareTwoDate(String begain, String end) {
		int para = 0;
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_FULL);
		try {
			Date begainDate = formatter.parse(begain);
			Date endDate = formatter.parse(end);
			para = begainDate.compareTo(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return para;
	}

	/**
	 * 日期比较 0等 -1小于 1大于
	 *
	 * @param month
	 * @return
	 */
	public static int compareTwoMonth(String month, String otherMonth) {
		String date = "yyyyMMdd";
		int para = 0;
		Calendar calendar2 = Calendar.getInstance();
		try {
			calendar2.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(otherMonth));
		} catch (ParseException e) {
			logger.error("compareTwoMonth日期比较" + e);
		}
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat(DEFAULT_FORMAT).parse(month));
		} catch (ParseException e) {
			logger.error("compareTwoMonth日期比较" + e);
		}
		para = calendar.compareTo(calendar2);
		return para;

	}

	/**
	 * 得到当前时间，例如"20021106"
	 */
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return formatter.format(date);
	}

	/**
	 * 得到指定日期，例如"20021106"
	 */
	public static String getCurrentMonthDay(String day) {
		try {
			if (StringUtils.isEmpty(day)) {
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int setDay = Integer.valueOf(day);
			if (endDay < setDay) {
				setDay = endDay;
			}
			calendar.set(Calendar.DAY_OF_MONTH, setDay);
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			logger.error("getCurrentMonthDay得到指定日期异常" + e);
		}
		return null;
	}

	/**
	 * 取得一个月的天数
	 *
	 * @param year 实际年份
	 * @param mon  实际月份 1到12月
	 */
	public static int getDaysOfMonth(int year, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mon - 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 取得一个月的天数
	 *
	 * @param date 年月
	 */
	public static int getDaysOfMonth(String date) throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date temp = sdf.parse(date);
		cal.setTime(temp);
		return getDaysOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	public static String formatDate(String pattern, Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}


	public static Date parseDate(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		Date date = null;
		try {
			date = org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					new String[]{DATE_FORMAT_YMDI, DATE_FORMAT_FULL, DATE_FORMAT_FULL_II, DATE_FORMAT_YMD,
							DATE_FORMAT_HMS, DATE_FORMAT_YMD_II, DATE_FORMAT_DAY_YEAR_II,
							DATE_FORMAT_DAY_YEAR_II_UNIT});
		} catch (Exception ex) {
			logger.error("Pase the Date(" + dateString + ") occur errors:" + ex.getMessage());
		}
		return date;
	}

	/**
	 * 获取月份差值
	 *
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
		ArrayList<String> result = new ArrayList<String>();

		Calendar min = Calendar.getInstance();
		min.setTime(DateUtils.parseDate(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		Calendar max = Calendar.getInstance();
		max.setTime(DateUtils.parseDate(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(DateUtils.formatDate(DateUtils.DATE_FORMAT_DAY_YEAR_II, curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}

	/**
	 * 功能描述: 获取两个日期之间的天数
	 *
	 * @Date: 2019/12/16 20:44
	 **/
	public static long getDistanceOfTwoDate(String startDate, String endDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date before = sdf.parse(startDate);
		Date after = sdf.parse(endDate);
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static int getMonthDiff(String startDate, String endDate) {
		startDate = startDate.substring(0, 6) + "01";
		endDate = endDate.substring(0, 6) + "01";
		//当前时间
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime start = formatter.parseDateTime(startDate);
		DateTime end = formatter.parseDateTime(endDate);
		//end-start 结果可为负数、正数、0
		int months = Months.monthsBetween(start, end).getMonths();
		//取绝对值
		return Math.abs(months);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getTimeAfterMonths(new SimpleDateFormat("yyyyMMdd").format(new Date()), -18).substring(0, 8));
	}

	public static String getNow(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * @Description: 月份添加
	 * @param @param day
	 * @param @param cycle
	 * @param @return
	 * @return String
	 * @throws
	 * @author
	 */
	public static String addMonth(String day, int cycle) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isEmpty(day)) {
			calendar.setTime(new Date());
		} else {
			try {
				calendar.setTime(formatter.parse(day));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		calendar.add(Calendar.MONTH, Integer.valueOf(cycle));
		return getLastDayOfMonth(calendar.getTime());
	}
}