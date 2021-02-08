package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @author 熊锋
 * @date 2021-01-13 17:20
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class RepairCloseVo {

    /**
     * id
     */
    private Long id;

    /**
     * 原因
     */
    private String remark;

}
