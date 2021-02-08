package com.spring.base.vo.baseinfo.user;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 业主app用户详情VO
 * @Author:吕文祥
 * @UpdateDate:2020/4/24 9:44
 */
@ApiModel
@Data
public class CustomerAppUserDetailVo {
    // 主键
    @ApiModelProperty(value = "用户ID")
    private String userId;
}
