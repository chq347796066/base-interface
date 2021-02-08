package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 月份时间戳Vo
 *
 * @author WuJiaQuan
 * @date 2020/2/25 下午2:54
 */
@ApiModel
public class TimestampMonthVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    private String month;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private List<String> dayList;

    public TimestampMonthVo() {
    }

    public TimestampMonthVo(String month) {
        this.setMonth(month);
    }

    public TimestampMonthVo(String month, List<String> dayList) {
        this.setMonth(month);
        this.setDayList(dayList);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<String> getDayList() {
        return dayList;
    }

    public void setDayList(List<String> dayList) {
        this.dayList = dayList;
    }
}
