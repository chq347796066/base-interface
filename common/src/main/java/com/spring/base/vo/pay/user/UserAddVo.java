package com.spring.base.vo.pay.user;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-12 16:29:15
* @Desc类说明: 新增vo
*/

@ApiModel
@Data
@ToString
public class UserAddVo {

   //用户代码
   @ApiModelProperty(value = "用户代码")
   private String userCode;

   //用户名
   @ApiModelProperty(value = "用户名")
   private String userName;

   //国籍
   @ApiModelProperty(value = "国籍")
   private String nation;

   //性别(1男,2女)
   @ApiModelProperty(value = "性别(1男,2女)")
   private Integer sex;

   //邮箱
   @ApiModelProperty(value = "邮箱")
   private String email;

   //电话
   @ApiModelProperty(value = "电话")
   private String mobile;

   //生日
   @ApiModelProperty(value = "生日")
   private Date birthday;

   //身份证
   @ApiModelProperty(value = "身份证")
   private String idCard;

   //毕业学校
   @ApiModelProperty(value = "毕业学校")
   private String school;

   //学历
   @ApiModelProperty(value = "学历")
   private String education;

   //地址
   @ApiModelProperty(value = "地址")
   private String address;

    //角色id列表,角色id逗号分隔
    @ApiModelProperty(value = "角色id列表,角色id逗号分隔")
    private String roleIdList;
}
