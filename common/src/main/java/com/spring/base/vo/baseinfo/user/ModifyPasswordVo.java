package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:修改用户密码vo
 * @author: 赵进华
 * @time: 2020/4/9 16:37
 */
@ApiModel
@Data
@ToString
public class ModifyPasswordVo {

    @ApiModelProperty(value = "用户帐号")
    private String userCode;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "密码")
    private String password;
}
