package com.spring.base.vo.pay.billdetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-27 16:43:07
* @Desc类说明: 账单详情实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class BillDetailVo {
   private static final long serialVersionUID = 1L;
   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   @ApiModelProperty(value = "01未缴 02已缴")
   private String chargeStatus;
}
