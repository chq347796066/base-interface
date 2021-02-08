package com.spring.base.vo.pay.payonekeypay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ApiModel
@Data
@ToString
public class PrePayVo {

    //未缴金额
    @ApiModelProperty(value = "未缴金额")
    private String unpaidAmount;

    //收费时间
    @ApiModelProperty(value = "收费时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
