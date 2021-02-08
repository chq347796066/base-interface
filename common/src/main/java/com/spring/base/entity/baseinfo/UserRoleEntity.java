package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 15:59:52
* @Desc类说明: 实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("b_user_role")
public class UserRoleEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //
   @ApiModelProperty(value = "")
   private String id;

   //角色id
   @ApiModelProperty(value = "角色id")
   private String roleId;

   // 用户id
   @ApiModelProperty(value = " 用户id")
   private String userId;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //租户id
//   @ApiModelProperty(value = "租户id")
//   private String tenantId;

   @ApiModelProperty(value = "用户姓名")
   private String userName;

   @ApiModelProperty(value = "角色名称")
   private String roleName;

   @ApiModelProperty(value = "用户手机号")
   private String userCode;

   @ApiModelProperty(value = "公司名称")
   private String companyName;
}
