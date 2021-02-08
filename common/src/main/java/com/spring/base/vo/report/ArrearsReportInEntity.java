package com.spring.base.vo.report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 欠费统计
 */
@Data
@ApiModel(description = "欠费统计")
public class ArrearsReportInEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "公司ID")
    private String compayId;

    @JsonIgnore
    private List<String> compayIdList;

    @ApiModelProperty(name = "小区ID")
    private String communityId;

    @ApiModelProperty(name = "费项NO")
    private String chargeNo;

    @ApiModelProperty(name = "计费开始周期")
    private String stateDate;

    @ApiModelProperty(name = "计费结束周期")
    private String endDate;

}
