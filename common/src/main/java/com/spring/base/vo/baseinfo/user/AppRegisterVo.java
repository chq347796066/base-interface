package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:app注册vo
 * @author: 赵进华
 * @time: 2020/4/9 16:06
 */
@ApiModel
@Data
@ToString
public class AppRegisterVo {

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "密码")
    private String password;
}
