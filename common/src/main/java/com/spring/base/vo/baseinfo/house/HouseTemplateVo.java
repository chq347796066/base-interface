package com.spring.base.vo.baseinfo.house;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明: 房产信息实体类
*/
@ApiModel
@Data
public class HouseTemplateVo {
   private static final long serialVersionUID = 1L;

   @Excel(name = "小区名称")
   private String communityName;

   @Excel(name = "楼栋")
   private String buildName;

   @Excel(name = "单元")
   private String cellName;

   @Excel(name = "楼层")
   private String floor;

   @Excel(name = "门牌号")
   private String houseNo;

   //房屋编码
   @Excel(name = "房屋编码")
   private String houseCode;

   //房间类型
   @Excel(name = "房屋类型")
   private String houseType;

   //房间状态
   @Excel(name = "房屋状态")
   private String  houseStatus;

   //建筑面积
   @Excel(name = "建筑面积")
   private String architectureArea;

   @Excel(name = "使用面积")
   private String useArea;

   //客户姓名
   @Excel(name = "客户姓名")
   private String customerName;

   //性别(1男,2女)
   @Excel(name = "客户性别")
   private String sex;

   //客户类型 1 个人 2单位 3开发商
   @Excel(name = "客户类别")
   private String customerType;

   //联系电话
   @Excel(name = "客户电话")
   private String phone;

   //证件类型 1 身份证
   @Excel(name = "证件类型")
   private String certificatesType;

   //证件号码
   @Excel(name = "客户证件")
   private String certificatesNumber;

}
