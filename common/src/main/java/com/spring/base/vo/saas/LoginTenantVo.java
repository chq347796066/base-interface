package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月23日 上午11:11:59
 * @Desc类说明:登录后返回vo
 */
@ApiModel
@Data
@ToString
public class LoginTenantVo {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户Code")
    private String userCode;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "租户状态")
    private int tenantStatus;

    @ApiModelProperty(value = "token")
    private String token;

}
