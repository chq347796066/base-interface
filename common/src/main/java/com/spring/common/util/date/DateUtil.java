package com.spring.common.util.date;

import com.spring.common.util.string.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:时间工具类
 */
public class DateUtil {
    /**
     * 缺省的日期各个部分之间的连结符号
     */
    private static String mark_date = "-";
    private static int mark_date_len = 1;

    /**
     * 缺省的时间各个部分之间的连结符号
     */
    private static String mark_time = ":";

    private static int mark_time_len = 1;

    private static String dtspace = " ";
    private static int dtspace_len = 1;

    private static int long_date_len = 10;
    private static int med_date_len = 8;

    private static int long_time_len = 8;
    private static int med_time_len = 5;

    private static int long_datetime_len = 19;
    private static int med_datetime_len = 14;

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
    public static final int[] DAYS_OF_MONTH_IN_NORMAL_YEAR = new int[]{
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    /**
     * 在闰年中，各个月份的天数
     */
    public static final int[] DAYS_OF_MONTH_IN_LEAP_YEAR = new int[]{
            31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    static {
        updateVar();
    }

    public DateUtil() {

    }

    /**
     * @description:指定日期往后加月份
     * @return:
     * @author: 赵进华
     * @time: 2020/7/9 13:51
     */
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 在格式改变的时候,重新计算各个部分的长度
     */
    private static void updateVar() {
        long_date_len = 4 + mark_date_len + 2 + mark_date_len + 2;
        med_date_len = 2 + mark_date_len + 2 + mark_date_len + 2;

        long_time_len = 2 + mark_time_len + 2 + mark_time_len + 2;
        med_time_len = 2 + mark_time_len + 2;

        long_datetime_len = long_date_len + dtspace_len + long_time_len;
        med_datetime_len = med_date_len + dtspace_len + med_time_len;
    }

    /**
     * 设置日期格式的间隔符号
     *
     * @param mark 新的间隔符号
     */
    public static void setDateMark(String mark) {
        mark_date = mark;
        mark_date_len = mark.length();
        updateVar();
    }

    /**
     * 取得当前设置的日期之间的分隔符
     *
     * @return 日期之间的分隔符
     */
    public static String getDateMark() {
        return mark_date;
    }

    /**
     * 设置时间格式的间隔符号
     *
     * @param mark 新的间隔符号
     */
    public static void setTimeMark(String mark) {
        mark_time = mark;
        mark_time_len = mark.length();
        updateVar();
    }

    /**
     * 取得当前设置的时间之间的分隔符号
     *
     * @return 时间之间的分隔符号
     */
    public static String getTimeMark() {
        return mark_time;
    }

    /**
     * 设置日期和时间之间的分割符号
     *
     * @param vDtSpace 分割符号字符串
     */
    public static void setDtSpace(String vDtSpace) {
        dtspace = vDtSpace;
        dtspace_len = dtspace.length();
        updateVar();
    }

    /**
     * 取得当前的时间的字符串形式，包括年月日 时分秒
     *
     * @param dateSpace 日期各个部分之间的分隔符号
     * @param timeSpace 时间各个部分之间的分隔符号
     * @return 按照指定格式的当前时间的描述(YYYY - MM - DD hh : mm : dd)
     */
    public static String getNowStr(String dateSpace, String timeSpace) {
        Calendar c = Calendar.getInstance();
        return getDateTime(c, dateSpace, timeSpace);
    }

    /**
     * 取得一个日期时间字符串的日期部分
     *
     * @param dtstr 日期时间字符串
     * @return 日期部分的字符串
     */
    public static String getDatePart(String dtstr) {
        return StrUtil.getLeftStr(dtstr, dtspace);
    }

    /**
     * 取得一个日期时间字符串的时间部分
     *
     * @param dtstr 日期时间字符串
     * @return 时间部分的字符串
     */
    public static String getTimePart(String dtstr) {
        return StrUtil.getRightStr(dtstr, dtspace);
    }

    /**
     * 取得一个时间的中文格式
     *
     * @param c         时间日历
     * @param dateSpace 日期各个部分之间的分隔符号
     * @param timeSpace 时间各个部分之间的分隔符号
     * @return 按照指定格式的当前时间的描述(YYYY - MM - DD hh : mm : dd)
     */
    public static String getDateTime(Calendar c, String dateSpace, String timeSpace) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        StringBuffer str = new StringBuffer();
        str.append(toDateStr(year, month, day, dateSpace));
        str.append(dtspace);
        str.append(toTimeStr(hour, minute, second, timeSpace));
        return str.toString();
    }

    /**
     * 取得一个时间的中文格式
     *
     * @param date      以毫秒表示的日期时间
     * @param dateSpace 日期各个部分之间的分隔符号
     * @param timeSpace 时间各个部分之间的分隔符号
     * @return 按照指定格式的当前时间的描述(YYYY - MM - DD hh : mm : dd)
     */
    public static String getDateTime(long date, String dateSpace, String timeSpace) {
        Date d = new Date(date);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getDateTime(c, dateSpace, timeSpace);
    }

    /**
     * 将形如:YYYY-MM-DD hh:mm:ss的日期时间转化为中等长度的格式:YY-MM-DD hh:mm
     *
     * @param datetime 长日期时间格式字符串
     * @return 中等长度格式的日期
     */
    public static String toMedDateTime(String datetime) {
        if (datetime == null) {
            return null;
        }
        int length = datetime.length();
        if (length <= med_datetime_len) {
            return datetime;
        } else if (length == long_datetime_len) {
            return datetime.substring(2, length - (2 + mark_time_len));
        } else {
            throw new IllegalArgumentException("无法识别的日期字符串: " + datetime);
        }
    }

    /**
     * 按照缺省格式取得一个时间的中文格式
     *
     * @param date 以毫秒表示的日期时间
     * @return 按照指定格式的当前时间的描述(YYYY - MM - DD hh : mm : dd)
     */
    public static String getDateTime(long date) {
        return getDateTime(date, mark_date, mark_time);
    }

    /**
     * 取得当前的时间的字符串形式，包括年月日 时分秒,采用缺省的分隔符号
     *
     * @return 按照指定格式的当前时间的描述，格式为：(YYYY-MM-DD hh:mm:dd)
     */
    public static String getNowStr() {
        return getNowStr(mark_date, mark_time);
    }

    /**
     * 取得当前日期的4位年数字符串
     */
    public static String getYearStr() {
        Date dt = new Date();
        String tos = dt.toString();
        return tos.substring((tos.length() - 4));
    }

    /**
     * 取得短的年份表示YY
     */
    public static String getShortYearStr() {
        return getYearStr().substring(2, 4);
    }

    /**
     * 将一个长的日期时间(YYYY-MM-DD hh:mm:ss)转换成一个中等日期时间(YY-MM-DD hh:mm)
     *
     * @param longDateTime 长日期时间字符串
     * @return 中日期时间字符串
     */
    public static String longTime(String longDateTime) {
        return longDateTime.substring(2, med_datetime_len + 2);
    }

    /**
     * 取得当前日期的2位月数字符串
     */
    public static String getMonthStr() {
        Calendar rightNow = Calendar.getInstance();
        int month = rightNow.get(Calendar.MONTH) + 1;
        return (month > 9 && month < 13) ? "" + month : "0" + month;
    }

    /**
     * 取得当前日期的2位日字符串
     */
    public static String getDayStr() {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        return day < 10 ? "0" + day : "" + day;
    }

    /**
     * 取得当前日期的完整符串，格式YYYYMMDD
     */
    public static String getDateStr() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return toDateStr(year, month, day);
    }

    /**
     * 取得一个日期的年份
     *
     * @param date 符合日期格式的日期
     * @return 年份部分
     */
    public static int getYearOf(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    /**
     * 取得一个日期的月份
     *
     * @param date 符合日期格式的日期
     * @return 月份部分
     */
    public static int getMonthOf(String date) {
        return Integer.parseInt(date.substring(4 + mark_date_len, 6 + mark_date_len));
    }

    /**
     * 取得一个日期的日部分
     *
     * @param date 符合日期格式的日期
     * @return 日部分
     */
    public static int getDayOf(String date) {
        return Integer.parseInt(date.substring(4 + 2 + mark_date_len * 2));
    }

    /**
     * 取得在两个日期之间的年数
     *
     * @param startDate 8位开始日期
     * @param endDate   8位结束日期
     * @return 总的年数，包括开始年份和结束年份
     */
    public static int getYearCountBetween(String startDate, String endDate) {
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        return endYear - startYear + 1;
    }

    /**
     * 取得在两个日期之间的月数
     *
     * @param startDate 8位开始日期
     * @param endDate   8位结束日期
     * @return 总的月数，包括开始月份和结束月份
     */
    public static int getMonthCountBetween(String startDate, String endDate) {
        int startYear = getYearOf(startDate);
        int endYear = getYearOf(endDate);
        int startMonth = getMonthOf(startDate);
        int endMonth = getMonthOf(endDate);

        return (12 - startMonth + 1) + (endYear - startYear - 1) * 12 + endMonth;
    }

    /**
     * 取得在两个日期之间的天数
     *
     * @param startDate 8位开始日期
     * @param endDate   8位结束日期
     * @return 总的天数，包括开始和结束的日
     */
    public static int getDayCountBetween(String startDate, String endDate) {
        int startYear = getYearOf(startDate);
        int endYear = getYearOf(endDate);
        int startMonth = getMonthOf(startDate);
        int endMonth = getMonthOf(endDate);
        int startDay = getDayOf(startDate);
        int endDay = getDayOf(endDate);

        int days = 0;

        if (startYear < endYear) {
            for (int year = startYear + 1; year <= endYear - 1; year++) {
                days += getDaysOfYear(year);
            }
            for (int month = startMonth + 1; month <= 12; month++) {
                days += getDaysOfMonth(startYear, month);
            }
            for (int month = 1; month <= endMonth - 1; month++) {
                days += getDaysOfMonth(endYear, month);
            }
            days += getDaysOfMonth(startYear, startMonth) - startDay + 1;
            days += endDay;
        } else if (startMonth < endMonth) {
            for (int month = startMonth + 1; month < endMonth; month++) {
                days += getDaysOfMonth(startYear, month);
            }
            days += getDaysOfMonth(startYear, startMonth) - startDay + 1;
            days += endDay;
        } else {
            days = endDay - startDay + 1;
        }
        return days;
    }

    /**
     * 判断一个年份是否闰年
     *
     * @param year 年份
     * @return true - 是闰年，false - 不是闰年
     */
    public static boolean isLeapYear(int year) {
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
     * @param year 年份
     * @return 这一年的天数
     */
    public static int getDaysOfYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    /**
     * 计算一个月有多少天
     *
     * @param year  年份
     * @param month 月份, 1 -- 12
     * @return 这个月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        if (isLeapYear(year)) {
            return DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
        } else {
            return DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
        }
    }

    /**
     * 取得在两个日期之间的年数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 年数
     */
    public static int getYearCount(DateTime start, DateTime end) {
        int startYear = start.getYear();
        int endYear = end.getYear();
        return endYear - startYear;
    }

    /**
     * 取得两个日期之间的月份数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 月份数
     */
    public static int getMonthCount(DateTime start, DateTime end) {
        int result = getYearCount(start, end) * 12;
        return result + end.getMonth() - start.getMonth();
    }

    /**
     * 取得在两个日期之间的月数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 月数
     */
    public static int getDayCount(DateTime start, DateTime end) {
        int result = 0;
        for (int year = start.getYear(); year < end.getYear(); year++) {
            result += getDaysOfYear(year);
        }

        for (int month = 1; month < start.getMonth(); month++) {
            result -= getDaysOfMonth(start.getYear(), month);
        }
        result -= start.getDay();

        for (int month = 1; month < end.getMonth(); month++) {
            result += getDaysOfMonth(end.getYear(), month);
        }
        result += end.getDay();

        return result;
    }

    /**
     * 取得两个日期时间的小时数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 小时数
     */
    public static int getHourCount(DateTime start, DateTime end) {
        int result = getDayCount(start, end) * 24;
        return result + end.getHour() - start.getHour();
    }

    public static DateTime[] getArray(DateTime start, DateTime end, int segType) {
        return null;
    }


    /**
     * 取出两个日期之间的所有日期数据数组，包括开始日期和结束日期
     *
     * @param startDate 符合日期格式的开始日期
     * @param endDate   符合日期格式的结束日期
     * @param segType   分隔的类型
     * @return 日期字符串数组。当分隔方式无效时返回null.
     */

    public static String[] getDateArray(String startDate, String endDate, int segType) {

        String[] arr = null;
        int count = 0;

        int startYear = getYearOf(startDate);
        if (segType == DATE_SEG_YEAR) {
            count = getYearCountBetween(startDate, endDate);
            arr = new String[count];
            for (int i = 0; i < count; i++) {
                arr[i] = "" + (startYear + i);
            }
            return arr;
        }

        int startMonth = getMonthOf(startDate);
        if (segType == DATE_SEG_MONTH) {
            count = getMonthCountBetween(startDate, endDate);
            arr = new String[count];
            int year = startYear;
            int month = startMonth;
            for (int i = 0; i < count; i++) {
                arr[i] = fixToLength(year, 4) + mark_date + fixToLength(month, 2);
                month++;
                if (month == 13) {
                    month = 1;
                    year++;
                }
            }
            return arr;
        }

        int startDay = getDayOf(startDate);
        if (segType == DATE_SEG_DAY) {
            count = getDayCountBetween(startDate, endDate);
            arr = new String[count];
            int year = startYear;
            int month = startMonth;
            int day = startDay;
            for (int i = 0; i < count; i++) {
                arr[i] = fixToLength(year, 4) + mark_date + fixToLength(month, 2)
                        + mark_date + fixToLength(day, 2);
                day++;
                if (day > getDaysOfMonth(year, month)) {
                    day = 1;
                    month++;
                    if (month > 12) {
                        month = 1;
                        year++;
                    }
                }
            }
            return arr;
        }

        throw new IllegalArgumentException("Invalid segment type: " + segType);
    }

    /**
     * 取得当前时间字符串，用默认的格式进行格式化
     * 结果中，每个域均为2个字符，即类似于：19:09:09
     *
     * @return 当前时间字符串
     */
    public static String getTimeStr() {
        return getTimeStr(mark_time);
    }

    /**
     * 描述:时间戳转换为时间
     * 参数: @param s
     * 参数: @return
     * 返回值类型: String
     * 作者: 姜宏杰
     * 日期: 2018年1月30日 下午10:07:07
     */
    public static String stampToDate(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 取得当前时间字符串，用默认的格式进行格式化
     * 结果中，每个域均为2个字符，即类似于：19:09:09
     *
     * @param space 时间的间隔字符串
     * @return 当前时间字符串
     */
    public static String getTimeStr(String space) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        StringBuffer result = new StringBuffer();
        if (hour < 10) {
            result.append("0");
        }
        result.append(hour);

        if (space != null) {
            result.append(space);
        }
        if (minute < 10) {
            result.append("0");
        }
        result.append(minute);

        if (space != null) {
            result.append(space);
        }
        if (second < 10) {
            result.append("0");
        }
        result.append(second);

        return result.toString();
    }

    /**
     * 对一个日期部分补足长度
     *
     * @param val 日期部分
     * @param len 希望的长度
     * @return 固定长度的字符串
     */
    public static String fixToLength(int val, int length) {
        String valStr = "" + val;

        if (valStr.length() >= length) {
            return valStr;
        }

        StringBuffer buff = new StringBuffer();

        for (int i = valStr.length(); i < length; i++) {
            buff.append("0");
        }
        return buff.append(valStr).toString();
    }

    /**
     * 计算指定日期前或者后几天的日期
     *
     * @param date 指定日期
     * @param days 天数，> 0 表示几天后； < 0 表示几天前
     * @return 符合格式规定的日期字符串
     */
    public static final String getDateAt(String date, int days) {
        if (days > 0) {
            return getDateAfter(date, days);
        } else {
            return getDateBefore(date, -days);
        }
    }

    /**
     * 计算某一天之后的几天后的日期字符串
     *
     * @param date 符合日期格式开始日期
     * @param days 以后的天数
     * @return 以后的日期字符串，符合日期格式规定
     */
    public static String getDateAfter(String date, int days) {
        int year = getYearOf(date);
        int month = getMonthOf(date);
        int day = getDayOf(date);

        //计算是否为闰年
        boolean asLeap = isLeapYear(year);
        day = day + days;

        while (isValidDay(year, month, day) == false) {
            if (asLeap) {
                day -= DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
            } else {
                day -= DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
            }

            month++;
            if (month > 12) {
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
     * @param date 符合日期格式的开始日期
     * @param days 以前的天数
     * @return 以前日期字符串，符合日期格式规定
     */
    public static String getDateBefore(String date, int days) {
        int year = getYearOf(date);
        int month = getMonthOf(date);
        int day = getDayOf(date);

        //计算是否为闰年
        boolean asLeap = isLeapYear(year);
        day = day - days;

        while (isValidDay(year, month, day) == false) {
            month--;
            if (month == 0) {
                year--;
                month = 1;
                asLeap = isLeapYear(year);
            }

            if (asLeap) {
                day += DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
            } else {
                day += DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
            }
        }
        return toDateStr(year, month, day);
    }

    /**
     * 检查一个日期时间字符串是否有效
     *
     * @param dateTimeStr 日期时间字符串
     * @return true 有效；false 无效
     */
    public static boolean isValidDateTime(String dateTimeStr) {
        try {
            String dateStr = StrUtil.getLeftStr(dateTimeStr, dtspace);
            if (isValidDate(dateStr) == false) {
                return false;
            }

            String timeStr = StrUtil.getRightStr(dateTimeStr, dtspace);
            return isValidTime(timeStr) != false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 取得一个时间字符串中的小时数
     *
     * @param timeStr 时间字符串
     * @return 小时
     */
    public static int getHourOf(String timeStr) {
        return Integer.parseInt(timeStr.substring(0, 2));
    }

    /**
     * 取得一个日期时间字符串的小时数
     *
     * @param dateStr 日期时间格式的字符串
     * @param asLong  是否完整的日期时间
     * @return
     */
    public static int getHourOf(String dateStr, boolean asFull) {
        if (asFull) {
            dateStr = getTimePart(dateStr);
        }
        return Integer.parseInt(StrUtil.getLeftStr(dateStr, mark_time));
    }

    /**
     * 取得一个时间字符串中的分钟数
     *
     * @param timeStr 时间字符串
     * @return 分钟数
     */
    public static int getMinuteOf(String timeStr) {
        return Integer.parseInt(timeStr.substring(2 + mark_time_len,
                2 + mark_time_len + 2));
    }

    /**
     * 取得一个时间字符串中的秒数
     *
     * @param timeStr 时间字符串
     * @return 秒数
     */
    public static int getSecondOf(String timeStr) {
        return Integer.parseInt(timeStr.substring(2 + 2 + mark_time_len * 2));
    }

    /**
     * 检查一个时间字符串是否有效
     *
     * @param timeStr 时间字符串
     * @return true 有效；false 无效
     */
    public static boolean isValidTime(String timeStr) {
        return isValidTime(timeStr, true);
    }

    /**
     * 检查一个时间的各个部分是否有效
     *
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return true 有效；false 无效
     */
    public static boolean isValidTime(int hour, int minute, int second) {
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
     * @param dateStr 日期字符串
     * @return true 有效；false 无效
     */
    public static boolean isValidDate(String dateStr) {
        if (dateStr.length() != getDateStrLen()) {
            return false;
        }
        if (dateStr.indexOf(mark_date) != 4) {
            return false;
        }
        if (dateStr.indexOf(mark_date, 4 + mark_date.length()) != 6 + mark_date.length()) {
            return false;
        }
        try {
            int year = getYearOf(dateStr);
            int month = getMonthOf(dateStr);
            int day = getDayOf(dateStr);
            return isValidDate(year, month, day);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 检查构成日期的各个部分是否有效
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return true 有效；false 无效
     */
    public static boolean isValidDate(int year, int month, int day) {
        if (day <= 0) {
            return false;
        }

        boolean asLeap = isLeapYear(year);
        if (asLeap) {
            return day <= DAYS_OF_MONTH_IN_LEAP_YEAR[month - 1];
        } else {
            return day <= DAYS_OF_MONTH_IN_NORMAL_YEAR[month - 1];
        }
    }

    /**
     * 检查某一个日期在某一个年份中的某一个月中是否存在
     *
     * @param year  年份
     * @param month 月
     * @param day   日期
     * @return true 日期存在；false 日期不存在
     */
    public static boolean isValidDay(int year, int month, int day) {
        return isValidDate(year, month, day);
    }

    /**
     * 检查一个事件字符串的内容是否是合格的时间描述
     *
     * @param timeStr 时间字符串
     * @param asLong  是否完整的时间：false - hh:mm; true - hh:mm:ss
     * @return true 数据有效；false 数据无效
     */
    public static boolean isValidTime(String timeStr, boolean asLong) {
        if (timeStr.length() != getTimeStrLen(asLong)) {
            return false;
        }

        try {
            OutInteger pos = new OutInteger(0);
            int hour = StrUtil.getIntToken(timeStr, mark_time, pos);
            int minute = StrUtil.getIntToken(timeStr, mark_time, pos);
            int sec = asLong ? StrUtil.getIntToken(timeStr, mark_time, pos) : 0;
            return isValidTime(hour, minute, sec);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 返回一个可以代表指定的时间的毫秒数
     *
     * @param dateTimeStr 日期时间字符串，符合格式规定
     * @return 毫秒数
     */
    public static long getTimeMillis(String dateTimeStr) {
        OutInteger pos = new OutInteger(0);
        int year = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, mark_date, pos));
        int month = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, mark_date, pos));
        int day = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, dtspace, pos));
        int hour = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, mark_time, pos));
        int minute = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, mark_time, pos));
        int second = Integer.parseInt(StrUtil.getStrToken(dateTimeStr, mark_time, pos));

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hour, minute, second);
        return c.getTime().getTime();
    }

    public static long getTimeMillis(DateTime time) {
        Calendar c = Calendar.getInstance();
        c.set(time.getYear(), time.getMonth() - 1, time.getDay(),
                time.getHour(), time.getMinute(), time.getSecond());
        return c.getTime().getTime();
    }

    /**
     * 从日期构造一个字符串
     *
     * @param year  年份
     * @param month 月
     * @param day   日
     * @return 日期字符串
     */
    public static String toDateStr(int year, int month, int day) {
        return toDateStr(year, month, day, mark_date);
    }

    /**
     * 从日期构造一个字符串
     *
     * @param year  年份
     * @param month 月
     * @param day   日
     * @return 日期字符串
     */
    public static String toDateStr(int year, int month, int day, String dateMark) {
        String yearStr = fixToLength(year, 4);
        String monthStr = fixToLength(month, 2);
        String dayStr = fixToLength(day, 2);
        return new StringBuffer(20).append(yearStr).append(dateMark)
                .append(monthStr).append(dateMark).append(dayStr).toString();
    }

    /**
     * 根据给定的日期、时间的各个部分，构造一个日期-时间字符串
     *
     * @param year   年份
     * @param month  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @param sec    秒
     * @return 日期-时间字符串
     */
    public static String toDateTimeStr(int year, int month, int day,
                                       int hour, int minute, int sec) {
        String dateStr = toDateStr(year, month, day);
        String timeStr = toTimeStr(hour, minute, sec);
        return dateStr + dtspace + timeStr;
    }

    /**
     * 将一个常日期格式的字符串转换成只有月日部分的字符串
     *
     * @param dateStr yyyy-mm-dd 格式的日期字符串
     * @return 短日期格式：mm-dd
     */
    public static String toShortDate(String longDateStr) {
        if (longDateStr.length() != getDateStrLen()) {
            throw new IllegalArgumentException("日期格式错误: " + longDateStr);
        }
        return longDateStr.substring(4 + mark_date_len);
    }

    /**
     * 将一个完整的时间字符产转变为短格式，只包含小时，分钟
     *
     * @param longTimeStr 原来的时间字符串，格式：hh:mm:ss
     * @return 短时间格式 hh:mm
     */
    public static String toShortTime(String longTimeStr) {
        if (longTimeStr.length() != getTimeStrLen(true)) {
            throw new IllegalArgumentException("时间格式错误: " + longTimeStr);
        }
        return longTimeStr.substring(0, 4 + mark_time_len);
    }

    /**
     * 取得当前日期格式设定的日期字符串的长度
     *
     * @return 日期字符串长度
     */
    public static int getDateStrLen() {
        return 4 + mark_date_len + 2 + mark_date_len + 2;
    }

    /**
     * 取得当前日期格式设定的日期字符串的长度
     *
     * @return 日期字符串的长度
     */
    public static int getTimeStrLen(boolean asLong) {
        return asLong ? (2 * 3 + mark_time_len * 2) : (2 * 2 + mark_time_len);
    }

    /**
     * 将指定的小时、分钟、秒合成一个时间字符串
     *
     * @param hour   小时
     * @param minute 分钟
     * @param sec    秒
     * @return 时间字符串
     */
    public static String toTimeStr(int hour, int minute, int sec, String timeMark) {
        StringBuffer sb = new StringBuffer(fixToLength(hour, 2));
        sb.append(timeMark).append(fixToLength(minute, 2));
        sb.append(timeMark).append(fixToLength(sec, 2));
        return sb.toString();
    }

    /**
     * 将指定的小时、分钟、秒合成一个时间字符串
     *
     * @param hour   小时
     * @param minute 分钟
     * @param sec    秒
     * @return 时间字符串
     */
    public static String toTimeStr(int hour, int minute, int sec) {
        return toTimeStr(hour, minute, sec, mark_date);
    }

    /**
     * 将当前月日时分秒时间转化为一个10位数字的整数,例如"2004-12-25 15:06:18"=>1225150618
     *
     * @return 一个表示当前时间的整数
     */
    public static int getTime() {
        Calendar c = Calendar.getInstance();
        int num = c.get(Calendar.YEAR);
        num = num * 100 + c.get(Calendar.MONTH) + 1;
        num = num * 100 + c.get(Calendar.DAY_OF_MONTH);
        num = num * 100 + c.get(Calendar.HOUR_OF_DAY);
        num = num * 100 + c.get(Calendar.MINUTE);
        num = num * 100 + c.get(Calendar.SECOND);
        return num;
    }

    public static String getDatePattern() {
        String defaultDatePattern = "yyyy-MM-dd";
        return defaultDatePattern;
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
        return df.format(date);
    }

    public static Long startTime() {
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
        try {
            return df.parse(df.format(new Date())).getTime();
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date dateTime(String dateStr) {
        // T代表后面跟着时间，Z代表UTC统一时间
        Date date = formatD(dateStr);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatD(String dateStr) {
        return formatD(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date formatD(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date ret = null;
        try {
            ret = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            //
        }
        return ret;
    }

    public static String formatD(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String ret = null;
        try {
            ret = simpleDateFormat.format(date);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 获取当前时间字符串
     *
     * @param format
     * @return
     */
    public static String getCurrent(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @return
     * @Author Liu Pinghui
     * @Description 获取当前时间到凌晨0点的秒数
     * @Date 2019/7/23 10:38
     * @Param
     **/
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /*********************************20200520**************************************/
    /**
     * @return
     * @Method: getDateStr
     * @Description: 获取指定日期时间字符串
     * @Auter: Mollen
     * @Date: 2020/5/20  15:39
     **/
    public static String getDateStr(Date date, String format) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            result = formatter.format(date);
        } catch (Exception e) {
            result = "格式有误";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return
     * @Method: getDateStr
     * @Description: 获取当前时间前一天日期字符串
     * @Auter: Mollen
     * @Date: 2020/5/20  15:39
     **/
    public static String getDateStrBefore(String format) {

        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dNow);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dBefore = calendar.getTime();
        return getDateStr(dBefore, format);

    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String getFormatDate(Date date) {
        String str = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }


}
