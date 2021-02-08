package com.spring.common.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * DateHelperExt
 *
 * @author WuJiaQuan
 */
public class DateHelperExt {

    /**
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取当前时间的标准格式
     * @date 2020-1-6 10:22
     */
    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * @param date
     * @param type
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将时间按照一定格式转化为字符串格式
     * @date 2020-1-6 10:22
     */
    public static String convertDateToString(Date date, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    /**
     * @param date
     * @param type
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将时间字符串按照一定格式转化为字符串格式
     * @date 2020-1-6 10:22
     */
    public static String convertDateToString(String date, String type)
            throws ParseException {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(type);
        DateFormat dateFormat = new SimpleDateFormat(type);
        result = format.format(dateFormat.parse(date));
        return result;
    }

    /**
     * @param str
     * @param type
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将时间字符串按照一定格式转化为字符串格式
     * @date 2020-1-6 10:22
     */
    public static Date convertStringToDate(String str, String type)
            throws ParseException {
        DateFormat dd = new SimpleDateFormat(type);
        Date result = null;
        try {
            result = dd.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param pattern
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取SimpleDateFormat
     * @date 2020-1-6 10:21
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        return new SimpleDateFormat(pattern);
    }

    /**
     * @param date
     * @param dateType
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期中的某数值
     * @date 2020-1-6 10:21
     */
    private static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * @param date
     * @param dateType
     * @param amount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期中某类型的某数值
     * @date 2020-1-6 10:21
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = dateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * @param date
     * @param dateType
     * @param amount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期中某类型的某数值
     * @date 2020-1-6 10:21
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * @param timestamps
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取精确的日期
     * @date 2020-1-6 10:21
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>(16);
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i), timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (timestampsLastTmp != null && timestampsLastTmp.length > 0) {
                        if (absoluteValues.size() > 1) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else if (absoluteValues.size() == 1) {
                            // 当timestamps的size为2，需要与当前时间作为参照
                            long dateOne = timestampsLastTmp[0];
                            long dateTwo = timestampsLastTmp[1];
                            if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
                                timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                            } else {
                                long now = System.currentTimeMillis();
                                if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                    timestamp = dateOne;
                                } else {
                                    timestamp = dateTwo;
                                }
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 判断字符串是否为日期字符串
     * @date 2020-1-6 10:20
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (stringToDate(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期字符串的日期风格
     * @date 2020-1-6 10:20
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<>(16);
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            Date dateTmp = stringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为日期
     * @date 2020-1-6 10:20
     */
    public static Date stringToDate(String date) {
        DateStyle dateStyle = null;
        return stringToDate(date, dateStyle);
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为日期
     * @date 2020-1-6 10:20
     */
    public static Date stringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * @param date
     * @param dateStyle
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为日期
     * @date 2020-1-6 10:20
     */
    public static Date stringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle == null) {
            List<Long> timestamps = new ArrayList<Long>();
            for (DateStyle style : DateStyle.values()) {
                Date dateTmp = stringToDate(date, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                }
            }
            myDate = getAccurateDate(timestamps);
        } else {
            myDate = stringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期转化为日期字符串
     * @date 2020-1-6 10:20
     */
    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * @param date
     * @param dateStyle
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期转化为日期字符串
     * @date 2020-1-6 10:19
     */
    public static String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为另一日期字符串
     * @date 2020-1-6 10:19
     */
    public static String oldStringToNewString(String date, String pattern) {
        return oldStringToNewString(date, null, pattern);
    }

    /**
     * @param date
     * @param dateStyle
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为另一日期字符串
     * @date 2020-1-6 10:19
     */
    public static String oldStringToNewString(String date, DateStyle dateStyle) {
        return oldStringToNewString(date, null, dateStyle);
    }

    /**
     * @param date
     * @param oldPattern
     * @param newPattern
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为另一日期字符串
     * @date 2020-1-6 10:19
     */
    public static String oldStringToNewString(String date, String oldPattern, String newPattern) {
        String dateString = null;
        if (null != newPattern) {
            if (oldPattern == null) {
                if (null != date) {
                    DateStyle style = getDateStyle(date);
                    if (style != null) {
                        Date myDate = stringToDate(date, style.getValue());
                        if (myDate != null) {
                            dateString = dateToString(myDate, newPattern);
                        }
                    }
                }
            } else {
                Date myDate = stringToDate(date, oldPattern);
                if (null != myDate) {
                    dateString = dateToString(myDate, newPattern);
                }
            }
        }
        return dateString;
    }

    /**
     * @param date
     * @param olddDteStyle
     * @param newDateStyle
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 将日期字符串转化为另一日期字符串
     * @date 2020-1-6 10:34
     */
    public static String oldStringToNewString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (null != newDateStyle) {
            if (olddDteStyle == null) {
                if (null != date) {
                    DateStyle style = getDateStyle(date);
                    if (null != style) {
                        dateString = oldStringToNewString(date, style.getValue(), newDateStyle.getValue());
                    }
                }
            } else {
                dateString = oldStringToNewString(date, olddDteStyle.getValue(), newDateStyle.getValue());
            }
        }
        return dateString;
    }

    /**
     * @param date
     * @param yearAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的年份
     * @date 2020-1-6 9:30
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * @param date
     * @param yearAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的年份
     * @date 2020-1-6 9:30
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * @param date
     * @param yearAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的月份
     * @date 2020-1-6 9:29
     */
    public static String addMonth(String date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * @param date
     * @param yearAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的月份
     * @date 2020-1-6 9:29
     */
    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * @param date
     * @param dayAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的天数
     * @date 2020-1-6 9:29
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * @param date
     * @param dayAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的天数
     * @date 2020-1-6 9:29
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的小时
     * @date 2020-1-6 9:29
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的小时
     * @date 2020-1-6 9:29
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }


    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的分钟
     * @date 2020-1-5 17:15
     */
    public static String addMinute(String date, int hourAmount) {
        return addInteger(date, Calendar.MINUTE, hourAmount);
    }

    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的分钟
     * @date 2020-1-5 17:15
     */
    public static Date addMinute(Date date, int hourAmount) {
        return addInteger(date, Calendar.MINUTE, hourAmount);
    }

    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的秒钟
     * @date 2020-1-5 17:15
     */
    public static String addSecond(String date, int hourAmount) {
        return addInteger(date, Calendar.SECOND, hourAmount);
    }

    /**
     * @param date
     * @param hourAmount
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期的秒钟
     * @date 2020-1-5 17:15
     */
    public static Date addSecond(Date date, int hourAmount) {
        return addInteger(date, Calendar.SECOND, hourAmount);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的年份
     * @date 2020-1-5 17:15
     */
    public static int getYear(String date) {
        return getYear(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的年份
     * @date 2020-1-5 17:15
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }


    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的月份
     * @date 2020-1-5 17:13
     */
    public static int getMonth(String date) {
        return getMonth(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的月份
     * @date 2020-1-5 17:13
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的天数
     * @date 2020-1-5 17:13
     */
    public static int getDay(String date) {
        return getDay(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的天数
     * @date 2020-1-5 17:12
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的小时
     * @date 2020-1-5 17:12
     */
    public static int getHour(String date) {
        return getHour(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的小时
     * @date 2020-1-5 17:11
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的分钟
     * @date 2020-1-5 17:11
     */
    public static int getMinute(String date) {
        return getMinute(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的分钟
     * @date 2020-1-5 17:11
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的秒钟
     * @date 2020-1-5 17:10
     */
    public static int getSecond(String date) {
        return getSecond(stringToDate(date));
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的秒钟
     * @date 2020-1-5 17:10
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期 默认yyyy-MM-dd格式
     * @date 2020-1-5 17:09
     */
    public static String getDate(String date) {
        return oldStringToNewString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期 默认yyyy-MM-dd格式
     * @date 2020-1-5 17:06
     */
    public static String getDate(Date date) {
        return dateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的时间 默认HH:mm:ss格式
     * @date 2020-1-5 17:09
     */
    public static String getFormatTime(String date) {
        return oldStringToNewString(date, DateStyle.HH_MM_SS);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取日期的时间 默认HH:mm:ss格式
     * @date 2020-1-5 17:05
     */
    public static String getFormatTime(Date date) {
        return dateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取间隔天数
     * @date 2020-1-5 17:03
     */
    public static int getIntervalDay(Date date, Date otherDate) {
        if (date != null && otherDate != null) {
            date = stringToDate(getDate(date));
            if (date != null) {
                long time = Math.abs(date.getTime() - otherDate.getTime());
                return (int) time / (24 * 60 * 60 * 1000);
            }
        }
        return -1;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 判断 date 类型值不能为空
     * @date 2020-1-5 17:02
     */
    public static boolean isNotNullDate(Date date) {
        return null != date;
    }

    /**
     * @param d
     * @param i
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 获取 ？ 天前的日期值
     * @date 2020-1-5 17:02
     */
    public static String addDate(Date d, long i) {
        SimpleDateFormat format = new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
        return format.format(new Date(d.getTime() - (i * 24 * 60 * 60 * 1000)));
    }

    /**
     * @param startDate     起始日期
     * @param addDays       增加天数
     * @param ignoreWeekend 是否跳过周末
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 增加日期天数，可以向前（传负值）或向后（传正值），可以选择跳过周末
     * @date 2020-1-5 17:01
     */
    public static Date addDayIgnoreWeekend(Date startDate, long addDays, boolean ignoreWeekend) {
        LocalDate localDate = dateToLocalDate(startDate);
        if (!ignoreWeekend) {
            return localDateToDate(localDate.plusDays(addDays));
        }
        return localDateToDate(localDate.with(addWorkingDays(addDays)));
    }

    private static TemporalAdjuster addWorkingDays(long workingDays) {
        return TemporalAdjusters.ofDateAdjuster(d -> addWorkingDays(d, workingDays));
    }

    private static LocalDate addWorkingDays(LocalDate date, long workingDays) {
        if (workingDays == 0) {
            return nextOrSameWorkingDay(date);
        }

        LocalDate result = date;
        int step = Long.signum(workingDays);

        for (long i = 0; i < Math.abs(workingDays); i++) {
            result = nextWorkingDay(result, step);
        }

        return result;
    }

    private static LocalDate nextOrSameWorkingDay(LocalDate date) {
        return isWeekEnd(date) ? nextWorkingDay(date, 1) : date;
    }

    private static LocalDate nextWorkingDay(LocalDate date, int step) {
        LocalDate localDate = date;
        do {
            localDate = localDate.plusDays(step);
        } while (isWeekEnd(localDate));

        return localDate;
    }

    private static Boolean isWeekEnd(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * @param localDate
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 本地时间转换标准时间
     * @date 2020-1-5 17:01
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @description 标准时间转换本地时间
     * @date 2020-1-5 17:00
     */
    private static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.toLocalDate();
    }
}
