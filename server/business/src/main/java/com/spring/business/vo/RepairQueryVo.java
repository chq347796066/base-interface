package com.spring.business.vo;

import com.spring.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 熊锋
 * @date 2021-01-06 17:42
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class RepairQueryVo extends PageVO {

    /**
     * 报修状态
     */
    @ApiModelProperty(value = "报修状态")
    private Integer repairStatus;

    /**
     * 渠道来源(0 业主端 1物管端)
     */
    @ApiModelProperty(value = "渠道来源")
    private Integer channelSource;
}
