package com.spring.base.vo.buiness.repairpersonnel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-11 16:09:13
* @Desc类说明: 更新报修派工人员vo
*/
@ApiModel
@Data
@ToString
public class RepairPersonnelUpdateVo  {

   //主键id
   @ApiModelProperty(value = "主键id")
   private String id;

    //业主报事报修主键id
    @ApiModelProperty(value = "业主报事报修主键id")
    private String reportingRepairId;

   //用户id
   @ApiModelProperty(value = "用户id")
   private String userId;

   //用户姓名
   @ApiModelProperty(value = "用户姓名")
   private String userName;

   //手机号码
   @ApiModelProperty(value = "手机号码")
   private String mobile;

   //预留字段
   @ApiModelProperty(value = "预留字段")
   private String repairReserve;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

}
