package com.spring.base.vo.pay.businessextend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 更新业务流水扩展vo
*/
@ApiModel
@Data
@ToString
public class BusinessExtendUpdateVo  {

   //业务扩展表主键
   @ApiModelProperty(value = "业务扩展表主键")
   private String id;

   //业务流水号
   @ApiModelProperty(value = "业务流水号")
   private String businessId;

   //交易流水号
   @ApiModelProperty(value = "交易流水号")
   private String transId;

   //计费方式
   @ApiModelProperty(value = "计费方式")
   private String chargeWay;

   //单价
   @ApiModelProperty(value = "单价")
   private String unitPrice;

   //面积
   @ApiModelProperty(value = "面积")
   private String area;

   //数量
   @ApiModelProperty(value = "数量")
   private String num;

   //起缴日
   @ApiModelProperty(value = "起缴日")
   private String startDate;

   //缴至日
   @ApiModelProperty(value = "缴至日")
   private String endDate;

   //本次读数
   @ApiModelProperty(value = "本次读数")
   private String thisReaded;

   //上次读数
   @ApiModelProperty(value = "上次读数")
   private String lastReaded;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

    //房屋类型
    @ApiModelProperty(value = "房屋类型")
    private String houseType;

    //房屋状态
    @ApiModelProperty(value = "房屋状态")
    private String houseStatus;

}
