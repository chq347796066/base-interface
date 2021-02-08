package com.spring.base.entity.pay;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.vo.Vo;
import com.spring.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 缴费账单实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("p_pay_bills")
public class PayBillsEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //账单编号
   @ApiModelProperty(value = "账单编号")
   private String billNo;

   //账单金额
   @ApiModelProperty(value = "账单金额")
   private String billAmount;

   //账单日期
   @ApiModelProperty(value = "账单日期")
   private String billDate;

   //客户号
   @ApiModelProperty(value = "客户号")
   private String custNo;

   //房屋编号
   @ApiModelProperty(value = "房屋编号")
   private String hid;

   //单元号
   @ApiModelProperty(value = "单元号")
   private String unitNo;

   //房号
   @ApiModelProperty(value = "房号")
   private String roomNo;

   //区域楼栋
   @ApiModelProperty(value = "区域楼栋")
   private String areaNo;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String plotId;

   //01未缴 02已缴 03欠费
   @ApiModelProperty(value = "01未缴 02已缴 03欠费")
   private String billStatus;

   //已缴金额
   @ApiModelProperty(value = "已缴金额")
   private String receivedAmount;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private Date updateTime;

   //业主编号
   @ApiModelProperty(value = "业主编号")
   private String ownerNo;

   //业主姓名
   @ApiModelProperty(value = "业主姓名")
   private String ownerName;

   //业主手机号
   @ApiModelProperty(value = "业主手机号")
   private String ownerMoble;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String plotName;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

   //01历史账单 02系统账单
   @ApiModelProperty(value = "01历史账单 02系统账单")
   private String billType;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

    //状态(1启用,2禁用)
    @ApiModelProperty(value = "状态(1启用,2禁用)")
    private Integer status;

    //房屋名称
    @ApiModelProperty(value = "房屋名称")
    @TableField(exist = false)
    private String houseName;

    //房屋编码
    @ApiModelProperty(value = "房屋编码")
    @TableField(exist = false)
    private String houseCode;

    //账单详情表
    @ApiModelProperty(value = "账单详情表")
    private List<BillDetailsEntity> billDetailsEntitys;
}
