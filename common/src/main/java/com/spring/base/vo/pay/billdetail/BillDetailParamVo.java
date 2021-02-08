package com.spring.base.vo.pay.billdetail;

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
public class BillDetailParamVo {

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

    //费项科目编码
    @ApiModelProperty(value = "费项科目编码")
    private String chargeType;

    //费项名称编码
    @ApiModelProperty(value = "费项名称编码")
    private String chargeNo;

    //开始时间
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    //结束时间
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    //账单日期
    @ApiModelProperty(value = "账单日期")
    private String billDate;

}
