package com.spring.common.util.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ibm
 */
public class DateToolUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 格式转换
     * @param date
     * @return
     */
    public static String DateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        String df = sdf.format(date);
        return df;
    }

    /**
     * @description:获取当前时间，格式YYYY_MM_DD
     * @return:
     * @author: 赵进华
     * @time: 2020/11/17 15:11
     */
    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String df = sdf.format(new Date());
        return df;
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final Date nowTime()
    {
    	Date date=null;
    	 try {
    		 date=new SimpleDateFormat(parsePatterns[5]).parse(new SimpleDateFormat(parsePatterns[5]).format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoorStr(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两个时间差
     */
    public static long  getDatePoorL(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day +  hour + min;
    }

    /**
     * 返回昨天
     * @param today
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String getday(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - i);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 返回今天
     * @param today
     * @return
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * string TO date
     */
    public static Date parseToDate(String ts)
    {
    	Date date=null;
        try {
        	date= new SimpleDateFormat().parse(ts);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }

    /**
     * @description:获取年月中的最后一天
     * @return:
     * @author: 赵进华
     * @time: 2020/10/18 20:11
     */
    public static String getLastDayOfMonth(String yearMonth) throws ParseException {
        String yearMonthString="";
        String yearStr=yearMonth.substring(0, 4);
        String monthStr=yearMonth.substring(yearMonth.length() -2,yearMonth.length());
        int year=Integer.parseInt(yearStr);
        int month=Integer.parseInt(monthStr);
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        yearMonthString= sdf.format(cal.getTime());
        return yearMonthString;
    }

    /**
     * @description:判断获取年月中的最后一天
     * @return:
     * @author: 赵进华
     * @time: 2020/10/18 20:11
     */
    public static boolean checkMonthEndDay(String date)  {
        boolean returnBool = false;
        String[] array=date.split("\\-");
        String yearMonthString = "";
        String yearStr ="";
        String monthStr ="";
        if(array.length==3) {
            yearStr = array[0];
            monthStr = array[1];
        }
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        yearMonthString = sdf.format(cal.getTime());

        if (date.equals(yearMonthString)) {
            returnBool = true;
        }
        return returnBool;
    }

    /**
     * @description:获取年月中的最后一天
     * @return:
     * @author: 赵进华
     * @time: 2020/10/18 20:11
     */
    public static String getMonthEndDay(String date)  {
        String yearMonthString = "";
        String yearStr=date.substring(0, 4);
        String monthStr=date.substring(date.length() -2,date.length());
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        yearMonthString = sdf.format(cal.getTime());
        return yearMonthString;
    }

    /**
     * @description:返回上个月的年月
     * @return:
     * @author: 赵进华
     * @time: 2020/10/22 15:07
     */
    public static String getLastYearMonth()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //当前月的上个月  （-1改为1的话，为取当前月的下个月）
        cal.add(Calendar.MONTH, -1);
        return sdf.format( cal.getTime());
    }

}
