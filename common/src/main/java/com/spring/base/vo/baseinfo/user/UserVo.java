package com.spring.base.vo.baseinfo.user;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Desc: 更改手机号实体类
 * @Author:邓磊
 * @UpdateDate:2020/4/24 9:44
 */
@ApiModel
@Data
public class UserVo {
    // 主键
    @ApiModelProperty(value = " 主键")
    @NotNull(message = "主键id不能为空")
    private String id;

    @ApiModelProperty(value = "验证码")
    @NotNull(message = "验证码能为空")
    private String verifyCode;

    //手机
    @ApiModelProperty(value = "手机号码")
    @NotNull(message = "手机号码不能为空")
    private String mobile;

}
