package com.spring.base.vo.pay.transjournals;

import lombok.Data;
import lombok.ToString;

/**
 * @author dell
 */
@Data
@ToString
public class TotalIncome {

    //公司id
    private String pcid;
    //今天开始时间
    private String firstCurrentDate;
    //当前时间
    private String currentDate;
    //昨天开始时间
    private String yesterdayStartDate;
    //昨天结束时间
    private String yesterdayEndDate;
    //本月第一天
    private String fristDayOfCurrentMonth;

}
