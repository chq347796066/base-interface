package com.spring.base.vo.pay.paybills;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class BillParamVo {

    //公司id
    @ApiModelProperty(value = "公司id")
    private String pcid;

    //小区id
    @ApiModelProperty(value = "小区id")
    private String cid;

    //房号id
    @ApiModelProperty(value = "房号id")
    private String hid;

    //费项编号
    @ApiModelProperty(value = "费项编号")
    private String chargeNo;
    //返回结果
    private String result;

    //当前日期
    @ApiModelProperty(value = "当前日期")
    private String curdate;

    //开始时间
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    //结束时间
    @ApiModelProperty(value = "结束时间")
    private String endDate;

}
