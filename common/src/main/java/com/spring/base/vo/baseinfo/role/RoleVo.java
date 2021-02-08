package com.spring.base.vo.baseinfo.role;

import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 15:37:54
* @Desc类说明: 角色实体类
*/
@ApiModel
@Data
public class RoleVo extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;
   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   @ApiModelProperty(value = "小区id")
   private String communityId;
}
