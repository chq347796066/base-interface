package com.spring.base.vo.pay.payonekeypay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BillOffsetVo {

    //公司id
    @ApiModelProperty(value = "公司id")
    private String pcid;

    //小区id
    @ApiModelProperty(value = "小区id")
    private String cid;

    //楼栋
    @ApiModelProperty(value = "楼栋")
    private String areaNo;

    //单元
    @ApiModelProperty(value = "单元")
    private String unitNo;

    //房号id
    @ApiModelProperty(value = "房屋编码")
    private String hid;


    //开始时间
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM")
    private String startDate;

    //结束时间
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM")
    private String endDate;


}
