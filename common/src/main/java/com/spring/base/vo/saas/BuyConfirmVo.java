package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description:购买应用vo
 * @author: 赵进华
 * @time: 2020/7/14 18:09
 */
@ApiModel
@Data
@ToString
public class BuyConfirmVo {

    @ApiModelProperty(value = "租户Id")
    @NotBlank(message = "租户Id不能为空")
    private String tenantId;

    @ApiModelProperty(value = "角色Id")
    @NotBlank(message = "角色Id不能为空")
    private String roleId;

    @ApiModelProperty(value = "购买日期")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buyDate;

    @ApiModelProperty(value = "小区数")
    private int communityNum;
}
