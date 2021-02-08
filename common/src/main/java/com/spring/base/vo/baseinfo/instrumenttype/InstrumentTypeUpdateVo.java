package com.spring.base.vo.baseinfo.instrumenttype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-02 17:17:10
* @Desc类说明: 更新仪类型vo
*/
@ApiModel
@Data
@ToString
public class InstrumentTypeUpdateVo  {

   //主键id
   @ApiModelProperty(value = "主键id")
   private Long id;

   //仪表类型
   @ApiModelProperty(value = "仪表类型")
   private String instrumentType;

   //计量单位
   @ApiModelProperty(value = "计量单位")
   private String measuringUnit;

   //仪表备注
   @ApiModelProperty(value = "仪表备注")
   private String remark;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

}
