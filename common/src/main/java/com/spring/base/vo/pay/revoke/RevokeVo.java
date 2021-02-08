package com.spring.base.vo.pay.revoke;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class RevokeVo {

        //交易号
        @ApiModelProperty(value = "交易号")
        private String transId;

        //撤销备注
        @ApiModelProperty(value = "撤销备注")
        private String undoReason;

        //操作员编号
        @ApiModelProperty(value = "操作员编号")
        private String operatorNo;

        //操作员姓名
        @ApiModelProperty(value = "操作员姓名")
        private String operatorName;

}
