package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 开票查询分页查询参数参数
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class InvoiceInquiryQueryVo {
    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String companyName;

    /**
     * 状态（1 待审核 2 开票失败 3 已开票）
     */
    @ApiModelProperty(value = "状态（1 待审核 2 开票失败 3 已开票）")
    private Integer invoiceStatus;

    /**
     * 申请时间-起始时间
     */
    @ApiModelProperty(value = "申请时间-起始时间")
    private String startDate;

    /**
     * 申请时间-结束时间
     */
    @ApiModelProperty(value = "申请时间-结束时间")
    private String endDate;
}
