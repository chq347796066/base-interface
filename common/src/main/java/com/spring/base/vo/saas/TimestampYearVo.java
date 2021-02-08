package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 年份时间戳Vo
 *
 * @author WuJiaQuan
 * @date 2020/2/25 下午2:55
 */
@ApiModel
public class TimestampYearVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    private String year;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    private List<TimestampMonthVo> monthList;

    public TimestampYearVo() {
    }

    public TimestampYearVo(String year) {
        this.setYear(year);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<TimestampMonthVo> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<TimestampMonthVo> monthList) {
        this.monthList = monthList;
    }
}
