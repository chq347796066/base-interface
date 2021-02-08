package com.spring.base.entity.report;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 欠费统计
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "欠费统计")
public class ArrearsReportEntity extends Model<ArrearsReportEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "公司ID")
    private String companyId;

    @ApiModelProperty(name = "公司名称")
    private String companyName;

    @ApiModelProperty(name="公司合计")
    private String aggregateAmount;

    @JsonIgnore
    private String chargeNameArr;

    @JsonIgnore
    private String paymentAmountArr;

    @ApiModelProperty(name="费项名称合集")
    private Map<String,String> chargeNameList;

    @ApiModelProperty(name="公司金额合集")
    private Map<String,String> paymentAmountList;

    @ApiModelProperty(name="总计")
    private Map<String,String> totalAmountList;




}
