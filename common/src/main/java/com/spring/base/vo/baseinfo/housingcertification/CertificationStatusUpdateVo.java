package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @className: CertificationStatusUpdateVo
 * @Description: 修改房屋认证状态
 * @author: shangshanshan
 * @date: 2021/1/5 11:33
 * @version: v1.0
 */

@ApiModel
@Data
@ToString
public class CertificationStatusUpdateVo {

    @ApiModelProperty(value = "数据id")
    private String id;

    @ApiModelProperty(value = "认证状态")
    private String certificationStatus;
}
