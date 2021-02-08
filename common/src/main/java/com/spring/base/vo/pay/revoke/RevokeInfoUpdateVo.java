package com.spring.base.vo.pay.revoke;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-05-20 10:37:32
* @Desc类说明: 更新撤销记录vo
*/
@ApiModel
@Data
@ToString
public class RevokeInfoUpdateVo  {

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String plotId;

   //流水号
   @ApiModelProperty(value = "流水号")
   private String transId;

   //撤销原因
   @ApiModelProperty(value = "撤销原因")
   private String undoReason;

   //操作员编号
   @ApiModelProperty(value = "操作员编号")
   private String operatorNo;

   //操作员姓名
   @ApiModelProperty(value = "操作员姓名")
   private String operatorName;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //反向流水号
   @ApiModelProperty(value = "反向流水号")
   private String revokeTransId;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

}
