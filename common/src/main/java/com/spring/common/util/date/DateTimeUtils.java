package com.spring.common.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:时间工具类
*/
public class DateTimeUtils {

//	private long lNow = System.currentTimeMillis();
//	private Calendar cNow = Calendar.getInstance();
//	private Date dNow = new Date(lNow);
//	private Timestamp tNow = new Timestamp(lNow);
//	private java.sql.Date today = new java.sql.Date(lNow);
//	private java.sql.Time now = new java.sql.Time(lNow);

	/**
	 * 默认构造方法
	 */
	public DateTimeUtils() {

	}

	/**
	 * 得到年
	 * 
	 * @param c
	 * @return
	 */
	public static int getYear(Calendar c) {
		if (c != null) {
			return c.get(Calendar.YEAR);
		} else {
			return Calendar.getInstance().get(Calendar.YEAR);
		}
	}
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    int dow = cal.get(Calendar.DAY_OF_WEEK);
	    int dom = cal.get(Calendar.DAY_OF_MONTH);
	    int doy = cal.get(Calendar.DAY_OF_YEAR);

	    System.out.println("Current Date: " + cal.getTime());
	    System.out.println("Day: " + day);
	    System.out.println("Month: " + month);
	    System.out.println("Year: " + year);
	}
	/**
	 * 得到月份 注意，这里的月份依然是从0开始的
	 * 
	 * @param c
	 * @return
	 */
	public static int getMonth(Calendar c) {
		if (c != null) {
			return c.get(Calendar.MONTH);
		} else {
			return Calendar.getInstance().get(Calendar.MONTH);
		}
	}
	/*
	/**
	 * 得到日期
	 * 
	 * @param c
	 * @return
	 */
	public static int getDate(Calendar c) {
		if (c != null) {
			return c.get(Calendar.DATE);
		} else {
			return Calendar.getInstance().get(Calendar.DATE);
		}
	}

	/**
	 * 得到星期
	 * 
	 * @param c
	 * @return
	 */
	public static int getDay(Calendar c) {
		if (c != null) {
			return c.get(Calendar.DAY_OF_WEEK);
		} else {
			return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		}
	}

	/**
	 * 得到小时
	 * 
	 * @param c
	 * @return
	 */
	public static int getHour(Calendar c) {
		if (c != null) {
			return c.get(Calendar.HOUR);
		} else {
			return Calendar.getInstance().get(Calendar.HOUR);
		}
	}

	/**
	 * 得到分钟
	 * 
	 * @param c
	 * @return
	 */
	public static int getMinute(Calendar c) {
		if (c != null) {
			return c.get(Calendar.MINUTE);
		} else {
			return Calendar.getInstance().get(Calendar.MINUTE);
		}
	}

	/**
	 * 得到秒
	 * 
	 * @param c
	 * @return
	 */
	public static int getSecond(Calendar c) {
		if (c != null) {
			return c.get(Calendar.SECOND);
		} else {
			return Calendar.getInstance().get(Calendar.SECOND);
		}
	}

	/**
	 * 得到指定或者当前时间前n天的Calendar
	 * 
	 * @param c
	 * @param n
	 * @return
	 */
	public static Calendar beforeDays(Calendar c, int n) {
		// 偏移量，给定n天的毫秒数
		long offset = n * 24 * 60 * 60 * 1000;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间后n天的Calendar
	 * 
	 * @param c
	 * @param n
	 * @return
	 */
	public static Calendar afterDays(Calendar c, int n) {
		// 偏移量，给定n天的毫秒数
		long offset = n * 24 * 60 * 60 * 1000;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
		return calendar;
	}

	/**
	 * 昨天
	 * 
	 * @param c
	 * @return
	 */
	public static Calendar yesterday(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 明天
	 * 
	 * @param c
	 * @return
	 */
	public static Calendar tomorrow(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间前offset毫秒的Calendar
	 * 
	 * @param c
	 * @param offset
	 * @return
	 */
	public static Calendar before(Calendar c, long offset) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间前offset毫秒的Calendar
	 * 
	 * @param c
	 * @param offset
	 * @return
	 */
	public static Calendar after(Calendar c, long offset) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 日期格式化
	 * 
	 * @param c
	 * @param pattern
	 * @return
	 */
	public static String format(Calendar c, String pattern) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}
		if (pattern == null || "".equals(pattern)) {
			pattern = "yyyy年MM月dd日 HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(calendar.getTime());
	}

	/**
	 * Date类型转换到Calendar类型
	 * 
	 * @param d
	 * @return
	 */
	public static Calendar date2Calendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	/**
	 * Calendar类型转换到Date类型
	 * 
	 * @param c
	 * @return
	 */
	public static Date calendar2Date(Calendar c) {
		return c.getTime();
	}

	/**
	 * Date类型转换到Timestamp类型
	 * 
	 * @param d
	 * @return
	 */
	public static Timestamp date2Timestamp(Date d) {
		return new Timestamp(d.getTime());
	}

	/**
	 * Calendar类型转换到Timestamp类型
	 * 
	 * @param c
	 * @return
	 */
	public static Timestamp calendar2Timestamp(Calendar c) {
		return new Timestamp(c.getTimeInMillis());
	}

	/**
	 * Timestamp类型转换到Calendar类型
	 * 
	 * @param ts
	 * @return
	 */
	public static Calendar timestamp2Calendar(Timestamp ts) {
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		return c;
	}

	/**
	 * 得到当前时间的字符串表示 格式2010-02-02 12:12:12
	 * 
	 * @return
	 */
	public static String getTimeString() {
		return format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 标准日期格式字符串解析成Calendar对象
	 * 
	 * @param s
	 * @return
	 */
	public static Calendar pars2Calender(String s) {
		Timestamp ts = Timestamp.valueOf(s);
		return timestamp2Calendar(ts);
	}

	/**
	 * 根据传过来的日期，返回日期当天最大值，如：2016-09-30 23:59
	 * @author 杨春
	 * @param d
	 * @return
	 */
	public static Date converMaxToday(Date d) {
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String efDate = sdf.format(d) + " 23:59";
		Date returnDate = new Date();
		try {
			returnDate = fmtDate.parse(efDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDate;
	}

	/**
	 * 据传过来的日期，返回日期当天最小值，如：2016-09-30
	 * @author 杨春
	 * @param d
	 * @return
	 */
	public static Date converMinToday(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date returnDate = d;
		try {
			returnDate = sdf.parse(sdf.format(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDate;
	}
	
	 /**
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    
    
    /** 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    
    public static Long getDatetime() {
    	
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	    try {
			return Long.valueOf(dateToStamp(sdf.format(new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return (long) 0;
    }
}
