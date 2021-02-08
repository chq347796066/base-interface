package com.spring.base.vo.baseinfo.pic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-14 15:31:38
* @Desc类说明: 新增图片中间vo
*/

@ApiModel
@Data
@ToString
public class PicAddVo {

   //外键ID
   @ApiModelProperty(value = "外键ID")
   private String tableId;

   //图片ID
   @ApiModelProperty(value = "图片ID")
   private String dataId;

   //图片名称
   @ApiModelProperty(value = "图片名称")
   private String name;

   //图片URL地址链接
   @ApiModelProperty(value = "图片URL地址链接")
   private String  url;

   //状态，1-启用，2-关闭
   @ApiModelProperty(value = "状态，1-启用，2-关闭")
   private Integer status;

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;


}
