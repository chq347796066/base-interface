package com.spring.common.util;

import com.spring.base.vo.saas.TimestampMonthVo;
import com.spring.base.vo.saas.TimestampYearVo;
import com.spring.common.enums.StatisticsTimeTypeEnum;
import com.spring.common.util.date.DateHelperExt;
import com.spring.common.util.date.DateStyle;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间戳工具类
 *
 * @author WuJiaQuan
 */
public class TimestampUtils {
    public static List<TimestampYearVo> getTimestamp(String start, String end, Integer statisticsTimeType) throws Exception {
        if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
            return null;
        }

        // 年份月份日期集合
        List<TimestampYearVo> timestampYearVoList = new ArrayList<>();

        // 格式化时间
        SimpleDateFormat sdf;
        // 起始时间
        Date startDate;
        // 结束时间
        Date endDate;
        // 设置时间
        Date tempDate;

        // 用Calendar 进行日期比较判断
        Calendar calendar = Calendar.getInstance();

        String tempYear = "";
        String tempMonth = "";

        // 年-月-日树形实体
        TimestampYearVo timestampYearVo = new TimestampYearVo();
        // 月-日时间树形集合
        List<TimestampMonthVo> timestampMonthVoList = new ArrayList<>();

        // 按年份统计
        if (statisticsTimeType.equals(StatisticsTimeTypeEnum.STATICS_BY_YEAR.getEnumCode())) {
            // 格式化时间
            sdf = new SimpleDateFormat(DateStyle.YYYY_MM.getValue());
            // 起始时间
            startDate = sdf.parse(start);
            // 结束时间
            endDate = sdf.parse(end);
            // 计数时间
            tempDate = startDate;
            // 按年份计算时间戳
            getTimestampByYear(timestampYearVoList, calendar, tempDate, endDate);
        }
        // 按月份统计
        if (statisticsTimeType.equals(StatisticsTimeTypeEnum.STATICS_BY_MONTH.getEnumCode())) {
            // 格式化时间
            sdf = new SimpleDateFormat(DateStyle.YYYY_MM.getValue());
            // 起始时间
            startDate = sdf.parse(start);
            // 结束时间
            endDate = sdf.parse(end);
            // 计数时间
            tempDate = startDate;
            // 按月份计算时间戳
            getTimestampByMonth(timestampYearVoList, timestampMonthVoList, timestampYearVo, calendar, tempYear, tempDate, endDate);
        }
        // 按日期统计
        if (statisticsTimeType.equals(StatisticsTimeTypeEnum.STATICS_BY_DAY.getEnumCode())) {
            // 格式化时间
            sdf = new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
            // 起始时间
            startDate = sdf.parse(start);
            // 结束时间
            endDate = sdf.parse(end);
            // 计数时间
            tempDate = startDate;
            // 按日期计算时间戳
            getTimestampByDay(timestampYearVoList, timestampYearVo, calendar, tempYear, tempMonth, tempDate, endDate);
        }

        return timestampYearVoList;
    }

    /**
     * 按年份计算时间戳
     *
     * @param timestampYearVoList
     * @param calendar
     * @param tempDate
     * @param endDate
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/2/26 下午1:49
     */
    private static void getTimestampByYear(List<TimestampYearVo> timestampYearVoList,
                                           Calendar calendar, Date tempDate, Date endDate) throws Exception {
        while (tempDate.getYear() <= endDate.getYear()) {
            // 设置当前时间
            calendar.setTime(tempDate);
            // 保存年份
            timestampYearVoList.add(new TimestampYearVo(String.valueOf(calendar.get(Calendar.YEAR))));
            // 当前时间年份+1
            calendar.add(Calendar.YEAR, 1);
            // 重新设置时间
            tempDate = calendar.getTime();
        }
    }

    /**
     * 按月份计算时间戳
     *
     * @param timestampYearVoList
     * @param timestampMonthVoList
     * @param timestampYearVo
     * @param calendar
     * @param tempYear
     * @param tempDate
     * @param endDate
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/2/26 下午1:50
     */
    private static void getTimestampByMonth(List<TimestampYearVo> timestampYearVoList,
                                            List<TimestampMonthVo> timestampMonthVoList, TimestampYearVo timestampYearVo,
                                            Calendar calendar, String tempYear, Date tempDate, Date endDate) throws Exception {
        while (tempDate.getTime() <= endDate.getTime()) {
            // 设置当前时间
            calendar.setTime(tempDate);
            // 取当前时间的年份
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            // 月份日期实体
            TimestampMonthVo timestampMonthVo;

            // 比较当前时间年份是否相等
            if (!year.equals(tempYear)) {
                // 年份不为空
                if (timestampYearVo.getYear() != null) {
                    // 保存月份
                    timestampYearVo.setMonthList(timestampMonthVoList);
                    timestampYearVoList.add(timestampYearVo);
                }
                // 重置年份
                timestampYearVo = new TimestampYearVo(year);
                // 重置月份
                timestampMonthVoList = new ArrayList<>();
            }

            // 保存月份
            timestampMonthVo = new TimestampMonthVo(DateHelperExt.dateToString(calendar.getTime(), DateStyle.YYYY_MM));
            timestampMonthVoList.add(timestampMonthVo);

            // 记录当前时间的年份
            tempYear = year;
            // // 当前时间月份+1
            calendar.add(Calendar.MONTH, 1);
            // 重新设置时间
            tempDate = calendar.getTime();

            // 保存当前时间年份+月份
            if (tempDate.getTime() > endDate.getTime()) {
                timestampYearVo.setMonthList(timestampMonthVoList);
                timestampYearVoList.add(timestampYearVo);
            }
        }
    }

    /**
     * 按日期计算时间戳
     *
     * @param timestampYearVoList
     * @param timestampYearVo
     * @param calendar
     * @param tempYear
     * @param tempMonth
     * @param tempDate
     * @param endDate
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/2/26 下午1:54
     */
    private static void getTimestampByDay(List<TimestampYearVo> timestampYearVoList, TimestampYearVo timestampYearVo,
                                          Calendar calendar, String tempYear, String tempMonth, Date tempDate, Date endDate) throws Exception {
        List<TimestampMonthVo> timestampMonthVoList = new ArrayList<>();

        while (tempDate.getTime() <= endDate.getTime()) {
            // 设置当前时间
            calendar.setTime(tempDate);
            // 取当前时间的年份
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            // 取当前时间的月份
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            // 取当前时间的日期
            Integer day = calendar.get(Calendar.DATE);
            // 取当前月份有几天
            Integer monthDayNum = calendar.getActualMaximum(Calendar.DATE);

            // 存月份日期实体
            TimestampMonthVo timestampMonthVo = new TimestampMonthVo();
            // 存日期集合
            List<String> dayTempList = new ArrayList<>();

            // 比较年份是否相等
            if (!year.equals(tempYear)) {
                // 重置年份
                timestampYearVo = new TimestampYearVo(year);
            }

            // 计算当前月份的日期
            Integer i = day;
            while (i <= monthDayNum && tempDate.getTime() <= endDate.getTime()) {

                // 比较当前年的月份是否相等
                if (!month.equals(tempMonth)) {
                    // 月份不为空
                    if (timestampMonthVo.getMonth() != null) {
                        // 保存日期
                        timestampMonthVo.setDayList(dayTempList);
                        timestampMonthVoList.add(timestampMonthVo);
                    }
                    // 重置月份
                    timestampMonthVo = new TimestampMonthVo(DateHelperExt
                            .dateToString(calendar.getTime(), DateStyle.YYYY_MM));
                    // 重置日期
                    dayTempList = new ArrayList<>();
                }

                // 添加日期
                dayTempList.add(DateHelperExt.dateToString(calendar.getTime(), DateStyle.YYYY_MM_DD));

                // 当前月份日期计算完成保存日期
                if (monthDayNum.equals(calendar.get(Calendar.DAY_OF_MONTH))) {
                    timestampMonthVo.setDayList(dayTempList);
                    timestampMonthVoList.add(timestampMonthVo);
                } else if (tempDate.getTime() >= endDate.getTime()) {
                    timestampMonthVo.setDayList(dayTempList);
                    timestampMonthVoList.add(timestampMonthVo);
                }

                // 记录当前时间的月份
                tempMonth = month;
                // 当前时间日期+1
                calendar.add(Calendar.DATE, 1);
                // 重新设置时间
                tempDate = calendar.getTime();

                ++i;
            }

            // 保存当前时间年份+月份+日期
            if ("12".equals(month) || tempDate.getTime() >= endDate.getTime()) {
                timestampYearVo.setMonthList(timestampMonthVoList);
                timestampYearVoList.add(timestampYearVo);
                timestampMonthVoList = new ArrayList<>();
            }
        }
    }
}
