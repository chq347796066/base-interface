package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明:  车位信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_car")
public class CarEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

    //客户id
    @ApiModelProperty(value = "客户id")
    private String customerId;

    //客户名称
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    //客户电话
    @ApiModelProperty(value = "客户电话")
    private String phone;

    //客户证件
    @ApiModelProperty(value = "客户证件")
    private String  certificatesNumber;

    //小区名称
    @ApiModelProperty(value = "小区名称")
   private String communityName;

   //车位编码
   @ApiModelProperty(value = "车位编码")
   private String carCode;

   //车牌号
   @ApiModelProperty(value = "车牌号")
   private String carNo;

   //车位面积
   @ApiModelProperty(value = "车位面积")
   private String area;

   //车位类型
   @ApiModelProperty(value = "车位类型")
   private Integer carType;

   //车位状态
   @ApiModelProperty(value = "车位状态")
   private Integer carStatus;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

    //文件类型
    @ApiModelProperty(value ="文件类型")
    private String excelType;

    //租户id
//    @ApiModelProperty(value = "租户id")
//    private String tenantId;

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;

}
