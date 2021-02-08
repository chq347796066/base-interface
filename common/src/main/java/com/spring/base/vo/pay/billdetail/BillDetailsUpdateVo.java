package com.spring.base.vo.pay.billdetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:13
* @Desc类说明: 更新账单详情vo
*/
@ApiModel
@Data
@ToString
public class BillDetailsUpdateVo  {

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //详情编号
   @ApiModelProperty(value = "详情编号")
   private String detailNo;

   //费项编号
   @ApiModelProperty(value = "费项编号")
   private String chargeNo;

    //费项名称
    @ApiModelProperty(value = "费项名称")
    private String chargeName;

    //楼栋
    @ApiModelProperty(value = "楼栋")
    private String building;

   //待缴金额
   @ApiModelProperty(value = "待缴金额")
   private String paymentAmount;

   //开始时间
   @ApiModelProperty(value = "开始时间")
   private String startDate;

   //结束时间
   @ApiModelProperty(value = "结束时间")
   private String endDate;

   //单价
   @ApiModelProperty(value = "单价")
   private String unitPrice;

   //数量
   @ApiModelProperty(value = "数量")
   private String useNumber;

   //滞纳金
   @ApiModelProperty(value = "滞纳金")
   private String dPenalty;

   //账单编号
   @ApiModelProperty(value = "账单编号")
   private String billNo;

   //01未缴 02已缴
   @ApiModelProperty(value = "01未缴 02已缴")
   private String chargeStatus;

   //01手机自助缴费  02服务中心收取 03上门收取
   @ApiModelProperty(value = "01手机自助缴费  02服务中心收取 03上门收取")
   private String chargeChannel;

   //水费电费时不能为空
   @ApiModelProperty(value = "水费电费时不能为空")
   private String paymentDate;

   //逾期日期
   @ApiModelProperty(value = "逾期日期")
   private String overdueDate;

   //上次读数
   @ApiModelProperty(value = "上次读数")
   private String lastReading;

   //本次读数
   @ApiModelProperty(value = "本次读数")
   private String thisReading;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //计费方式
   @ApiModelProperty(value = "计费方式")
   private String chargeWay;

   //已收金额
   @ApiModelProperty(value = "已收金额")
   private String receivedAmount;

   //车牌信息编号
   @ApiModelProperty(value = "车牌信息编号")
   private String licensePlate;

   //本次读数日期
   @ApiModelProperty(value = "本次读数日期")
   private String thisReadingdate;

   //缴费类型
   @ApiModelProperty(value = "缴费类型")
   private String chargeType;

   //缴费备注说明
   @ApiModelProperty(value = "缴费备注说明")
   private String chargeMark;

   //0否1是
   @ApiModelProperty(value = "0否1是")
   private String isCollectLatefee;

   //公式
   @ApiModelProperty(value = "公式")
   private String formula;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

   //上次读数日期
   @ApiModelProperty(value = "上次读数日期")
   private String lastReadingdate;

}
