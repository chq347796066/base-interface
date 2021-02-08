package com.spring.base.vo.item;

import com.spring.base.entity.item.HouseCharge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author dell
 */
@Data
@ApiModel(description = "房屋管理")
public class HouseChargeVO {

    @NotBlank(message = "小区ID不能为空")
    @ApiModelProperty(value = "小区ID")
    private String cid;

    @NotEmpty(message = "房屋不能为空")
    @ApiModelProperty(value = "房屋不能为空")
    private List<String> hids;

    @ApiModelProperty(value = "房屋管理对象")
    private List<HouseCharge> bhouseChargeList;
}
