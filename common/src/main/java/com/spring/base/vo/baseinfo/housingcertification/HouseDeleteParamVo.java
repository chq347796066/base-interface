package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class HouseDeleteParamVo {

    @ApiModelProperty(value = "数据id")
    private String id;

    //证件号码
    @ApiModelProperty(value = "证件号码")
    private Integer certificateNo;

    //房号id
    @ApiModelProperty(value = "房号id")
    private String houseId;

}
