package com.spring.base.vo.pay.transjournals;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 更新缴费记录vo
*/
@ApiModel
@Data
@ToString
public class TransJournalsUpdateVo  {

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //icipt开头
   @ApiModelProperty(value = "icipt开头")
   private String transId;

   //没有名字的统称游客，有别名的记别名
   @ApiModelProperty(value = "没有名字的统称游客，有别名的记别名")
   private String custName;

   //客户号：当前登录客户 客户号
   @ApiModelProperty(value = "客户号：当前登录客户 客户号")
   private String custNo;

   //i01-缴费i02-收费e03-退款i04-充值e05-提现e06-返还e07-抵充
   @ApiModelProperty(value = "i01-缴费 i02-收费 e03-退款 i04-充值 e05-提现 e06-返还 e07-抵充")
   private String transType;

   //01-周期性收费 02-临时性收费 03-履约金 04-非业主收费 05-在线缴费 06-人工代缴 07-历史欠费收费  08- 优惠补收 09-上线前预收 10-预缴费
   @ApiModelProperty(value = "01-周期性收费 02-临时性收费 03-履约金 04-非业主收费 05-在线缴费 06-人工代缴 07-历史欠费收费  08- 优惠补收 09-上线前预收 10-预缴费")
   private String transChargeType;

   //缴费金额
   @ApiModelProperty(value = "缴费金额")
   private String transAmount;

   //缴费时间
   @ApiModelProperty(value = "缴费时间")
   private String transTime;

   //00-未明01-未支付02-待复核03-完成04-撤销05失败06复核失败
   @ApiModelProperty(value = "00-未明01-未支付02-待复核03-完成04-撤销05失败06复核失败")
   private String transStatus;

   //开票状态（01：未开票，02：开票中，03:已开票，04：开票失败）
   @ApiModelProperty(value = "开票状态（01：未开票，02：开票中，03:已开票，04：开票失败）")
   private String transInvoiceStatus;

   //p-ios 缴费软件收费端  p-android缴费软件安卓版  t-tos收费软件ios版  t-android收费软件android版本 t-web收费软件web版内管
   @ApiModelProperty(value = "p-ios 缴费软件收费端  p-android缴费软件安卓版  t-tos收费软件ios版  t-android收费软件android版本 t-web收费软件web版内管")
   private String terminIdentify;

   //操作员姓名
   @ApiModelProperty(value = "操作员姓名")
   private String operatorName;

   //缴费端：当前登录客户号 （如果没登录则没有）收费端：操作员是收费员 （客户号）
   @ApiModelProperty(value = "缴费端：当前登录客户号 （如果没登录则没有）收费端：操作员是收费员 （客户号）")
   private String operatorNo;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String cid;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String cname;

   //公司编号
   @ApiModelProperty(value = "公司编号")
   private String companyId;

   //公司名
   @ApiModelProperty(value = "公司名")
   private String companyName;

   //电话
   @ApiModelProperty(value = "电话")
   private String phone;

   //标记
   @ApiModelProperty(value = "标记")
   private String rmark;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //收费备注
   @ApiModelProperty(value = "收费备注")
   private String chargeMark;

   //第三方支付手续费比例
   @ApiModelProperty(value = "第三方支付手续费比例")
   private String thirdPaymentServiceRate;

   //第三方支付手续费金额
   @ApiModelProperty(value = "第三方支付手续费金额")
   private String thirdPaymentServiceCharge;

    //房屋编号
    @ApiModelProperty(value = "房屋编号")
    private String houseCode;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

}
