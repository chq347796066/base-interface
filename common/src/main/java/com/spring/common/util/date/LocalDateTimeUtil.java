package com.spring.common.util.date;

import java.time.*;

/**
 * @author 熊锋
 * @date 2021-01-11 10:24
 * @Describe: jdk8 获取当天，本周，本月，本季度，本年起始时间工具类 LocalDateTime
 */
public class LocalDateTimeUtil {

    public static final String MinTime = "T00:00:00";
    public static final String MaxTime = "T23:59:59.999999999";

    /**
     * @Description:当天的开始时间
     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
     */
//    public static LocalDateTime getStartOrEndDayOfDay(LocalDate today, Boolean isFirst){
//        LocalDate resDate = LocalDate.now();
//        if (today == null) {
//            today = resDate;
//        }
//        if(isFirst){
//            return LocalDateTime.of(today, LocalTime.MIN);
//        }else{
//            return LocalDateTime.of(today, LocalTime.MAX);
//        }
//    }
//
//    /**
//     * @Description:本周的开始时间
//     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
//     */
//    public static LocalDateTime getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst){
//        String time = MinTime;
//        LocalDate resDate = LocalDate.now();
//        if (today == null) {
//            today = resDate;
//        }
//        DayOfWeek week = today.getDayOfWeek();
//        int value = week.getValue();
//        if (isFirst) {
//            resDate = today.minusDays(value - 1);
//        } else {
//            resDate = today.plusDays(7 - value);
//            time = MaxTime;
//        }
//        LocalDateTime localDateTime = LocalDateTime.parse(resDate.toString() + time);
//        return localDateTime;
//    }
//
//    /**
//     * @Description:本月的开始时间
//     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
//     */
//    public static LocalDateTime getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst){
//        String time = MinTime;
//        LocalDate resDate = LocalDate.now();
//        if (today == null) {
//            today = resDate;
//        }
//        Month month = today.getMonth();
//        int length = month.length(today.isLeapYear());
//        if (isFirst) {
//            resDate = LocalDate.of(today.getYear(), month, 1);
//        } else {
//            resDate = LocalDate.of(today.getYear(), month, length);
//            time = MinTime;
//        }
//        LocalDateTime localDateTime = LocalDateTime.parse(resDate.toString() + time);
//        return localDateTime;
//    }
//
//    /**
//     * @Description:本季度的开始时间
//     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
//     */
//    public static LocalDateTime getStartOrEndDayOfQuarter(LocalDate today, Boolean isFirst){
//        String time = MinTime;
//        LocalDate resDate = LocalDate.now();
//        if (today == null) {
//            today = resDate;
//        }
//        Month month = today.getMonth();
//        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
//        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
//        if (isFirst) {
//            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
//        } else {
//            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
//            time = MaxTime;
//        }
//        LocalDateTime localDateTime = LocalDateTime.parse(resDate.toString() + time);
//        return localDateTime;
//    }
//
//    /**
//     * @Description:本年度的开始时间
//     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
//     */
//    public static LocalDateTime getStartOrEndDayOfYear(LocalDate today, Boolean isFirst){
//        String time = MinTime;
//        LocalDate resDate = LocalDate.now();
//        if (today == null) {
//            today = resDate;
//        }
//        if (isFirst) {
//            resDate = LocalDate.of(today.getYear(), Month.JANUARY, 1);
//        } else {
//            resDate = LocalDate.of(today.getYear(), Month.DECEMBER, Month.DECEMBER.length(today.isLeapYear()));
//            time = MaxTime;
//        }
//        LocalDateTime localDateTime = LocalDateTime.parse(resDate.toString() + time);
//        return localDateTime;
//    }

    //本周的开始时间
    public static String getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        DayOfWeek week = today.getDayOfWeek();
        int value = week.getValue();
        if (isFirst) {
            resDate = today.minusDays(value - 1);
        } else {
            resDate = today.plusDays(7 - value);
        }
        return resDate.toString();
    }

    //本月的开始时间
    public static String getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        int length = month.length(today.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), month, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), month, length);
        }
        return resDate.toString();
    }

    //本季度开始时间
    public static String getStartOrEndDayOfQuarter(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        return resDate.toString();
    }

    //本年度开始时间
    public static String getStartOrEndDayOfYear(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), Month.JANUARY, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), Month.DECEMBER, Month.DECEMBER.length(today.isLeapYear()));
        }
        return resDate.toString();
    }

    public static void main(String[] args) {
        System.out.println("本周开始时间>>>" + getStartOrEndDayOfWeek(null, true));
        System.out.println("本周结束时间>>>" + getStartOrEndDayOfWeek(null, false));

        System.out.println("本月开始时间>>>" + getStartOrEndDayOfMonth(null, true));
        System.out.println("本月结束时间>>>" + getStartOrEndDayOfMonth(null, false));

        System.out.println("本季度开始时间>>>" + getStartOrEndDayOfQuarter(null, true));
        System.out.println("本季度结束时间>>>" + getStartOrEndDayOfQuarter(null, false));

        System.out.println("本年开始时间>>>" + getStartOrEndDayOfYear(null, true));
        System.out.println("本年结束时间>>>" + getStartOrEndDayOfYear(null, false));
    }
}
