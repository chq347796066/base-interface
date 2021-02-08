package com.spring.base.vo.pay.paidstatis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 实收统计
 * @author dell
 */
@Data
public class PaidStatisVo {
    /**
     * 费项科目
     */
    @ApiModelProperty(value = "费项科目")
    private String chargeTypeName;
    @ApiModelProperty(value = "费项名称支付方式")
    private List<ChargeNameGroup> chargeGroupList;
    @ApiModelProperty(value = "合计")
    private BigDecimal total;

}
