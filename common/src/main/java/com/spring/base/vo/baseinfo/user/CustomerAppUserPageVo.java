package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Desc: 业主app用户分页VO
 * @Author:吕文祥
 * @UpdateDate:2020/4/24 9:44
 */
@ApiModel
@Data
public class CustomerAppUserPageVo {
    // 主键
    @ApiModelProperty(value = "用户名")
    private String userName;
    //手机
    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "住戶類型")
    private String houseHoldType;

    //小区ids
    @ApiModelProperty(value = "小区ids")
    private List<String> communityIds;
}
